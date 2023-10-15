package com.emikhalets.convertapp.core.database

import android.content.Context
import com.emikhalets.convertapp.core.database.table_currencies.CurrenciesDao
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConvertDatabaseModule {

    @Provides
    fun providesConvertDatabase(@ApplicationContext context: Context): ConvertDatabase {
        return ConvertDatabase.getInstance(context)
    }

    @Provides
    fun providesCurrenciesDao(database: ConvertDatabase): CurrenciesDao {
        return database.currenciesDao
    }

    @Provides
    fun providesExchangesDao(database: ConvertDatabase): ExchangesDao {
        return database.exchangesDao
    }
}
