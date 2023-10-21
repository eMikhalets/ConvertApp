package com.emikhalets.convertapp.core.network

import com.emikhalets.convertapp.core.common.extensions.logd
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val currencyParser: CurrencyParser,
) {

    suspend fun parseExchanges(codes: List<String>): List<CurrencyDto> {
        logd("parseExchanges: $codes")
        return currencyParser.loadExchangesValues(codes)
    }
}
