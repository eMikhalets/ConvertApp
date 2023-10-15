package com.emikhalets.convertapp.core.network

import com.emikhalets.convertapp.core.common.extensions.execute
import com.emikhalets.convertapp.domain.AppResult
import javax.inject.Inject

class CurrencyRemoteDataSource @Inject constructor(
    private val currencyParser: CurrencyParser,
) {

    suspend fun parseExchanges(codes: List<String>): AppResult<List<CurrencyDto>> {
        return execute { currencyParser.loadExchangesValues(codes) }
    }
}
