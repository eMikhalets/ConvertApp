package com.emikhalets.convertapp.core.database

import com.emikhalets.convertapp.core.database.table_currencies.CurrenciesDao
import com.emikhalets.convertapp.core.database.table_currencies.CurrencyDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangeDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangesDao
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class LocalDataSource @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    private val exchangesDao: ExchangesDao,
) {

    fun getCurrencies(): Flow<List<CurrencyDb>> {
        return currenciesDao.getAllFlow()
    }

    fun getExchanges(): Flow<List<ExchangeDb>> {
        return exchangesDao.getAllFlow()
    }

    suspend fun updateExchanges(list: List<ExchangeDb>) {
        exchangesDao.update(list)
    }

    suspend fun isCurrencyExist(code: String): Boolean {
        return currenciesDao.isCodeExist(code)
    }

    suspend fun insertCurrency(model: CurrencyDb) {
        currenciesDao.insert(model)
    }

    suspend fun insertExchange(model: ExchangeDb) {
        exchangesDao.insert(model)
    }

    suspend fun insertExchanges(list: List<ExchangeDb>) {
        exchangesDao.insert(list)
    }

    suspend fun updateExchange(model: ExchangeDb) {
        exchangesDao.update(model)
    }

    suspend fun getCurrenciesSync(): List<CurrencyDb> {
        return currenciesDao.getAll()
    }

    suspend fun getExchangesSync(): List<ExchangeDb> {
        return exchangesDao.getAll()
    }

    suspend fun deleteCurrency(code: String) {
        currenciesDao.deleteByCode(code)
    }

    suspend fun deleteExchanges(code: String) {
        exchangesDao.deleteByCode(code)
    }

    suspend fun dropExchanges() {
        exchangesDao.drop()
    }
}
