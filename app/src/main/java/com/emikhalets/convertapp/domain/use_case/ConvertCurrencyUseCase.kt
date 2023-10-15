package com.emikhalets.convertapp.domain.use_case

import com.emikhalets.convertapp.domain.model.ExchangeModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConvertCurrencyUseCase @Inject constructor() {

    suspend operator fun invoke(
        currencies: List<Pair<String, Long>>,
        exchanges: List<ExchangeModel>,
        baseCode: String,
        baseValue: Long,
    ): List<Pair<String, Long>> {
        return withContext(Dispatchers.IO) {
            currencies.map { pair ->
                if (pair.first != baseCode) {
                    exchanges.find { it.containsPair(pair.first, baseCode) }
                        ?.let { Pair(pair.first, it.calculate(baseCode, baseValue)) }
                        ?: Pair(pair.first, 0)
                } else {
                    Pair(pair.first, baseValue)
                }
            }
        }
    }
}
