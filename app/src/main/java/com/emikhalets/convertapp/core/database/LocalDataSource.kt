package com.emikhalets.convertapp.core.database

import com.emikhalets.convertapp.core.common.extensions.logd
import com.emikhalets.convertapp.core.database.table_currencies.CurrenciesDao
import com.emikhalets.convertapp.core.database.table_currencies.CurrencyDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangeDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangesDao
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class LocalDataSource @Inject constructor(
    private val currenciesDao: CurrenciesDao,
    private val exchangesDao: ExchangesDao,
) {

    fun getCurrencies(): Flow<List<CurrencyDb>> {
        logd("getCurrencies")
        return currenciesDao.getAllFlow()
    }

    fun getExchanges(): Flow<List<ExchangeDb>> {
        logd("getExchanges")
        return exchangesDao.getAllFlow()
    }

    suspend fun updateExchanges(list: List<ExchangeDb>) {
        logd("updateExchanges: $list")
        exchangesDao.update(list)
    }

    suspend fun isCurrencyExist(code: String): Boolean {
        logd("isCurrencyExist: $code")
        return currenciesDao.isCodeExist(code)
    }

    suspend fun insertCurrency(model: CurrencyDb) {
        logd("insertCurrency: $model")
        currenciesDao.insert(model)
    }

    suspend fun insertExchange(model: ExchangeDb) {
        logd("insertExchange: $model")
        exchangesDao.insert(model)
    }

    suspend fun insertExchanges(list: List<ExchangeDb>) {
        logd("insertExchanges: $list")
        exchangesDao.insert(list)
    }

    suspend fun updateExchange(model: ExchangeDb) {
        logd("updateExchange: $model")
        exchangesDao.update(model)
    }

    suspend fun getCurrenciesSync(): List<CurrencyDb> {
        logd("getCurrenciesSync")
        return currenciesDao.getAll()
    }

    suspend fun getExchangesSync(): List<ExchangeDb> {
        logd("getExchangesSync")
        return exchangesDao.getAll()
    }

    suspend fun deleteCurrency(code: String) {
        logd("deleteCurrency: $code")
        currenciesDao.deleteByCode(code)
    }

    suspend fun deleteExchanges(code: String) {
        logd("deleteExchanges: $code")
        exchangesDao.deleteByCode(code)
    }

    suspend fun dropExchanges() {
        logd("dropExchanges")
        exchangesDao.drop()
    }
}
