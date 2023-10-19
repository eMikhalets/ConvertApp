package com.emikhalets.convertapp.core.network

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val currencyParser: CurrencyParser,
) {

    suspend fun parseExchanges(codes: List<String>): List<CurrencyDto> {
        return currencyParser.loadExchangesValues(codes)
    }
}