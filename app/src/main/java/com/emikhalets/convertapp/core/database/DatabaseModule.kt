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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        val name = "Convert.db"
        return Room
            .databaseBuilder(context, AppDatabase::class.java, name)
            .build()
    }

    @Provides
    fun provideCurrenciesDao(database: AppDatabase): CurrenciesDao {
        return database.currenciesDao
    }

    @Provides
    fun provideExchangesDao(database: AppDatabase): ExchangesDao {
        return database.exchangesDao
    }
}
