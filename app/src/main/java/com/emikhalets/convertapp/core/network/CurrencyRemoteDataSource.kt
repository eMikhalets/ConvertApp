package com.emikhalets.convertapp.core.network

import com.emikhalets.convertapp.core.common.extensions.execute
import com.emikhalets.convertapp.domain.model.AppResult
import com.emikhalets.convertapp.domain.model.CurrencyValue
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(
    private val currencyParser: CurrencyParser,
) {

    suspend fun parseExchanges(codes: List<String>): AppResult<List<CurrencyValue>> {
        return execute { currencyParser.loadExchangesValues(codes) }
    }
}
