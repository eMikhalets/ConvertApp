package com.emikhalets.convertapp.data

import com.emikhalets.convertapp.core.common.extensions.execute
import com.emikhalets.convertapp.core.database.ConvertLocalDataSource
import com.emikhalets.convertapp.core.database.table_currencies.CurrencyDb
import com.emikhalets.convertapp.core.database.table_currencies.CurrencyDb.Companion.toDb
import com.emikhalets.convertapp.core.database.table_currencies.CurrencyDb.Companion.toModelFlow
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangeDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangeDb.Companion.toDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangeDb.Companion.toDbList
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangeDb.Companion.toModelFlow
import com.emikhalets.convertapp.core.network.CurrencyRemoteDataSource
import com.emikhalets.convertapp.domain.AppResult
import com.emikhalets.convertapp.domain.model.CurrencyModel
import com.emikhalets.convertapp.domain.model.ExchangeModel
import java.util.Date
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class Repository @Inject constructor(
    private val localDataSource: ConvertLocalDataSource,
    private val remoteDataSource: CurrencyRemoteDataSource,
) {

    fun getExchanges(): Flow<List<ExchangeModel>> {
        return localDataSource.getExchanges().toModelFlow()
    }

    fun getCurrencies(): Flow<List<CurrencyModel>> {
        return localDataSource.getCurrencies().toModelFlow()
    }

    suspend fun updateExchanges(list: List<ExchangeModel>): AppResult<Unit> {
        return execute {
            val codes = list
                .filter { it.isNeedUpdate() }
                .map { it.getCode() }
            val updatedValues = remoteDataSource.parseExchanges(codes)
            val updatedDate = Date().time
            val newList = list.map { item ->
                updatedValues.find { it.code == item.getCode() }
                    ?.let { item.copy(value = it.value, date = updatedDate) } ?: item
            }
            localDataSource.updateExchanges(newList.toDbList())
        }
    }

    suspend fun insertCurrency(code: String): AppResult<Unit> {
        return execute {
            if (!localDataSource.isCurrencyExist(code)) {
                val currency = CurrencyModel(code).toDb()
                localDataSource.insertCurrency(currency)
                val currencies = localDataSource.getCurrenciesSync()
                val exchanges = localDataSource.getExchangesSync()
                when (currencies.count()) {
                    0 -> Unit
                    1 -> localDataSource.insertExchange(ExchangeModel(code).toDb())
                    2 -> localDataSource.updateExchange(exchanges.first().copy(sub = code))
                    else -> localDataSource.insertExchanges(currencies.createNewExchanges(code))
                }
            }
        }
    }

    suspend fun deleteCurrency(code: String): AppResult<Unit> {
        return execute {
            localDataSource.deleteCurrency(code)
            localDataSource.deleteExchanges(code)
            val currencies = localDataSource.getCurrenciesSync()
            if (currencies.count() == 1) {
                val lastCode = currencies.first().code
                localDataSource.dropExchanges()
                localDataSource.insertExchange(ExchangeModel(lastCode).toDb())
            }
        }
    }

    private fun List<CurrencyDb>.createNewExchanges(code: String): List<ExchangeDb> {
        return dropLast(1).map { ExchangeModel(it.code, code).toDb() }
    }
}
