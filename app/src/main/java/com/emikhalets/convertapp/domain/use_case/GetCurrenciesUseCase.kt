package com.emikhalets.convertapp.domain.use_case

import com.emikhalets.convertapp.core.common.extensions.logd
import com.emikhalets.convertapp.data.Repository
import com.emikhalets.convertapp.domain.model.CurrencyModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    operator fun invoke(): Flow<List<CurrencyModel>> {
        logd("GetCurrenciesUseCase")
        return repository.getCurrencies()
    }
}
