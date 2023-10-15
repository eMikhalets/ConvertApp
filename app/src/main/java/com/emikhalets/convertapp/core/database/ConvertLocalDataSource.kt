package com.emikhalets.convertapp.core.database

import com.emikhalets.convertapp.core.common.extensions.execute
import com.emikhalets.convertapp.core.database.table_currencies.CurrenciesDao
import com.emikhalets.convertapp.core.database.table_currencies.CurrencyDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangeDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangesDao
import com.emikhalets.convertapp.domain.AppResult
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ConvertLocalDataSource @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    private val exchangesDao: ExchangesDao,
) {

    fun getCurrencies(): Flow<List<CurrencyDb>> {
        return currenciesDao.getAllFlow()
    }

    fun getExchanges(): Flow<List<ExchangeDb>> {
        return exchangesDao.getAllFlow()
    }

    suspend fun updateExchanges(list: List<ExchangeDb>): AppResult<Unit> {
        return execute { exchangesDao.update(list) }
    }

    suspend fun isCurrencyExist(code: String): AppResult<Boolean> {
        return execute { currenciesDao.isCodeExist(code) }
    }

    suspend fun insertCurrency(model: CurrencyDb): AppResult<Unit> {
        return execute { currenciesDao.insert(model) }
    }

    suspend fun insertExchange(model: ExchangeDb): AppResult<Unit> {
        return execute { exchangesDao.insert(model) }
    }

    suspend fun insertExchanges(list: List<ExchangeDb>): AppResult<Unit> {
        return execute { exchangesDao.insert(list) }
    }

    suspend fun updateExchange(model: ExchangeDb): AppResult<Unit> {
        return execute { exchangesDao.update(model) }
    }

    suspend fun getCurrenciesSync(): AppResult<List<CurrencyDb>> {
        return execute { currenciesDao.getAll() }
    }

    suspend fun getExchangesSync(): AppResult<List<ExchangeDb>> {
        return execute { exchangesDao.getAll() }
    }

    suspend fun deleteCurrency(code: String): AppResult<Unit> {
        return execute { currenciesDao.deleteByCode(code) }
    }

    suspend fun deleteExchanges(code: String): AppResult<Unit> {
        return execute { exchangesDao.deleteByCode(code) }
    }

    suspend fun dropExchanges(): AppResult<Unit> {
        return execute { exchangesDao.drop() }
    }
}
