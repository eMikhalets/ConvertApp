package com.emikhalets.convertapp.presentation.screens

import androidx.compose.runtime.Immutable
import com.emikhalets.convertapp.core.common.mvi.MviAction
import com.emikhalets.convertapp.core.common.mvi.MviEffect
import com.emikhalets.convertapp.core.common.mvi.MviState
import com.emikhalets.convertapp.domain.StringValue
import com.emikhalets.convertapp.domain.model.ExchangeModel

object CurrenciesContract {

    @Immutable
    sealed class Action : MviAction {

        object AddCurrency : Action()
        object DropError : Action()
        object UpdateExchanges : Action()
        class DeleteCurrency(val code: String) : Action()

        sealed class Input : Action() {
            class BaseCodeClick(val code: String) : Input()
            class BaseValueChange(val value: String) : Input()
            class NewCurrencyChange(val value: String) : Input()
            class NewCurrencyVisible(val visible: Boolean) : Input()
        }
    }

    @Immutable
    sealed class Effect : MviEffect

    @Immutable
    data class State(
        val isLoading: Boolean = false,
        val exchanges: List<ExchangeModel> = emptyList(),
        val currencies: List<Pair<String, Long>> = emptyList(),
        val isOldExchanges: Boolean = false,
        val newCurrencyVisible: Boolean = false,
        val newCurrencyCode: String = "",
        val baseCode: String = "",
        val baseValue: String = "",
        val date: Long = 0,
        val error: StringValue? = null,
    ) : MviState
}
