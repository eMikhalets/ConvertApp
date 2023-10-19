package com.emikhalets.convertapp.core.database

import android.content.Context
import androidx.room.Room
import com.emikhalets.convertapp.core.database.table_currencies.CurrenciesDao
import com.emikhalets.convertapp.core.database.table_exchanges.ExchangesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val name = "Convert.db"
        return Room
            .databaseBuilder(context, AppDatabase::class.java, name)
            .build()
    }

    @Provides
    fun providesCurrenciesDao(database: AppDatabase): CurrenciesDao {
        return database.currenciesDao
    }

    @Provides
    fun providesExchangesDao(database: AppDatabase): ExchangesDao {
        return database.exchangesDao
    }
}
