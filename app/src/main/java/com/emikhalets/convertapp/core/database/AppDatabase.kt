package com.emikhalets.convertapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emikhalets.convertapp.core.database.table_currencies.CurrenciesDao
import com.emikhalets.convertapp.core.database.table_currencies.CurrencyDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangeDb
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangesDao

@Database(
    entities = [
        CurrencyDb::class,
        ExchangeDb::class,
    ],
    autoMigrations = [],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val currenciesDao: CurrenciesDao
    abstract val exchangesDao: ExchangesDao
}
