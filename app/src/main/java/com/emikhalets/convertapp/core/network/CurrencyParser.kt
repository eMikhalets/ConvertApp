package com.emikhalets.convertapp.core.network

import com.emikhalets.convertapp.core.common.extensions.loge
import javax.inject.Inject
import org.jsoup.nodes.Element

class CurrencyParser @Inject constructor() : AppParser() {

    private val source = "https://tradingeconomics.com/currencies?base="
    private val currencyRowKey = "[data-symbol*=CUR]"
    private val dataSymbolKey = "data-symbol"

    suspend fun loadExchangesValues(codes: List<String>): List<CurrencyDto> {
        return codes
            .map { it.take(3) }.toSet()
            .map { parseSource("$source$it") }
            .flatMap { it.getElements(currencyRowKey) }
            .filter { codes.containsDataSymbol(it) }
            .mapNotNull { convertData(it) }
    }

    private fun convertData(element: Element): CurrencyDto? {
        return try {
            val data = element.text().split(" ")
            val code = data[0]
            val value = data[1].toDoubleOrNull()
            checkNotNull(value)
            CurrencyDto(code, value)
        } catch (e: Exception) {
            loge(e)
            null
        }
    }

    private fun List<String>.containsDataSymbol(element: Element): Boolean {
        return try {
            val code = element.attr(dataSymbolKey).split(":").first()
            contains(code)
        } catch (e: IndexOutOfBoundsException) {
            loge(e)
            false
        }
    }
}
