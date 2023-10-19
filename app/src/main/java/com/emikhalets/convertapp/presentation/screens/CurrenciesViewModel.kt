package com.emikhalets.convertapp.presentation.screens

import androidx.lifecycle.viewModelScope
import com.emikhalets.convertapp.core.common.EmptyString
import com.emikhalets.convertapp.core.common.ZeroLong
import com.emikhalets.convertapp.core.common.mvi.MviViewModel
import com.emikhalets.convertapp.core.common.mvi.launch
import com.emikhalets.convertapp.domain.StringValue
import com.emikhalets.convertapp.domain.model.CurrencyModel
import com.emikhalets.convertapp.domain.model.ExchangeModel
import com.emikhalets.convertapp.domain.model.getUpdatedDate
import com.emikhalets.convertapp.domain.use_case.AddCurrencyUseCase
import com.emikhalets.convertapp.domain.use_case.ConvertCurrencyUseCase
import com.emikhalets.convertapp.domain.use_case.DeleteCurrencyUseCase
import com.emikhalets.convertapp.domain.use_case.GetCurrenciesUseCase
import com.emikhalets.convertapp.domain.use_case.GetExchangesUseCase
import com.emikhalets.convertapp.domain.use_case.UpdateExchangesUseCase
import com.emikhalets.convertapp.presentation.screens.CurrenciesContract.Action
import com.emikhalets.convertapp.presentation.screens.CurrenciesContract.Effect
import com.emikhalets.convertapp.presentation.screens.CurrenciesContract.State
import com.emikhalets.convertapp.core.common.date.startOfNextDay
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val addCurrencyUseCase: AddCurrencyUseCase,
    private val convertCurrencyUseCase: ConvertCurrencyUseCase,
    private val deleteCurrencyUseCase: DeleteCurrencyUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getExchangesUseCase: GetExchangesUseCase,
    private val updateExchangesUseCase: UpdateExchangesUseCase,
) : MviViewModel<Action, Effect, State>() {

    private var convertJob: Job? = null
    private var updatingJob: Job? = null

    private val currenciesFlow: Flow<List<CurrencyModel>> =
        flow { emitAll(getCurrenciesUseCase()) }
            .catch { setFailureState(it) }
            .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    private val exchangesFlow: Flow<List<ExchangeModel>> =
        flow { emitAll(getExchangesUseCase()) }
            .catch { setFailureState(it) }
            .shareIn(viewModelScope, SharingStarted.Eagerly, replay = 1)

    init {
        launch { exchangesFlow.collect { setExchangesState(it) } }
        launch { currenciesFlow.collect { setCurrenciesState(it) } }
    }

    override fun setInitialState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            Action.AddCurrency -> addCurrency()
            Action.DropError -> dropError()
            Action.UpdateExchanges -> updateExchanges()
            is Action.DeleteCurrency -> deleteCurrency(action.code)
            is Action.Input -> handleInputAction(action)
        }
    }

    private fun handleInputAction(action: Action.Input) {
        when (action) {
            is Action.Input.BaseCodeClick -> setBaseCode(action.code)
            is Action.Input.BaseValueChange -> setBaseValue(action.value)
            is Action.Input.NewCurrencyChange -> setNewCurrencyCode(action.value)
            is Action.Input.NewCurrencyVisible -> setNewCurrencyVisible(action.visible)
        }
    }

    private fun dropError() {
        setState { it.copy(error = null) }
    }

    private fun setNewCurrencyCode(code: String) {
        if (currentState.newCurrencyCode != code) {
            setState { it.copy(newCurrencyCode = code) }
        }
    }

    private fun setNewCurrencyVisible(visible: Boolean) {
        if (currentState.newCurrencyVisible != visible) {
            setState { it.copy(newCurrencyVisible = visible) }
        }
    }

    private fun setBaseCode(code: String) {
        if (currentState.baseCode != code) {
            setState { it.copy(baseCode = code) }
        }
    }

    private fun setBaseValue(value: String) {
        if (currentState.baseValue != value) {
            val valueValidated = value.toLongOrNull()?.toString() ?: EmptyString
            setState { it.copy(baseValue = valueValidated) }
            valueValidated.toLongOrNull()?.let {
                convert(it)
            } ?: run {
                val newCurrencies = currentState.currencies.map { it.copy(second = ZeroLong) }
                setState { it.copy(currencies = newCurrencies) }
            }
        }
    }

    private fun updateExchanges() {
        if (updatingJob?.isActive == true) updatingJob?.cancel()
        updatingJob = launch {
            setState { it.copy(isLoading = true) }
            when (val result = updateExchangesUseCase(currentState.exchanges)) {
                UpdateExchangesUseCase.Result.Success -> Unit
                is UpdateExchangesUseCase.Result.Failure -> {
                    setState { it.copy(error = result.error) }
                }
            }
            setState { it.copy(isLoading = false) }
        }
    }

    private fun addCurrency() {
        launch {
            val code = currentState.newCurrencyCode
            if (code.isNotBlank()) {
                when (val result = addCurrencyUseCase(code.take(3).uppercase())) {
                    AddCurrencyUseCase.Result.Success -> Unit
                    is AddCurrencyUseCase.Result.Failure -> {
                        setState { it.copy(error = result.error) }
                    }
                }
            }
            setState { it.copy(newCurrencyCode = "", newCurrencyVisible = false) }
        }
    }

    private fun deleteCurrency(code: String) {
        launch {
            if (code.isNotBlank()) {
                if (currentState.baseCode == code) {
                    setState { it.copy(baseCode = EmptyString) }
                }
                when (val result = deleteCurrencyUseCase(code)) {
                    DeleteCurrencyUseCase.Result.Success -> Unit
                    is DeleteCurrencyUseCase.Result.Failure -> {
                        setState { it.copy(error = result.error) }
                    }
                }
            }
        }
    }

    private fun convert(baseValue: Long) {
        if (convertJob?.isActive == true) convertJob?.cancel()
        convertJob = launch {
            delay(200)
            val currencies = currentState.currencies
            val exchanges = currentState.exchanges
            val baseCode = currentState.baseCode
            val newList = convertCurrencyUseCase(currencies, exchanges, baseCode, baseValue)
            setState { it.copy(currencies = newList) }
        }
    }

    private fun setCurrenciesState(list: List<CurrencyModel>) {
        val currencies = list.map { Pair(it.code, ZeroLong) }
        setState { it.copy(currencies = currencies) }
    }

    private fun setExchangesState(list: List<ExchangeModel>) {
        val needUpdate = list.any { it.isNeedUpdate() }
        val updatedDate = list.getUpdatedDate()
        setState { it.copy(exchanges = list, date = updatedDate, isOldExchanges = needUpdate) }
    }

    private fun setExchangesDateState(date: Long) {
        val hasOldExchanges = date.startOfNextDay() < Date().time
        setState { it.copy(date = date, isOldExchanges = hasOldExchanges) }
    }

    private fun setFailureState(throwable: Throwable) {
        setState { it.copy(isLoading = false, error = StringValue.create(throwable)) }
    }
}
