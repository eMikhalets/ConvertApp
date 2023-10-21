package com.emikhalets.convertapp.core.database.table_currencies

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesDao {

    @Insert
    suspend fun insert(item: CurrencyDb): Long

    @Insert
    suspend fun insert(list: List<CurrencyDb>): List<Long>

    @Update
    suspend fun update(item: CurrencyDb): Int

    @Update
    suspend fun update(list: List<CurrencyDb>): Int

    @Delete
    suspend fun delete(item: CurrencyDb): Int

    @Query("DELETE FROM currencies")
    suspend fun drop(): Int

    @Query("SELECT * FROM currencies")
    suspend fun getAll(): List<CurrencyDb>

    @Query("SELECT * FROM currencies")
    fun getAllFlow(): Flow<List<CurrencyDb>>

    @Query("SELECT * FROM currencies WHERE id = :id")
    suspend fun getItem(id: Long): CurrencyDb

    @Query("SELECT * FROM currencies WHERE id = :id")
    fun getItemFlow(id: Long): Flow<CurrencyDb>

    @Query("SELECT EXISTS(SELECT * FROM currencies WHERE code = :code)")
    suspend fun isCodeExist(code: String): Boolean

    @Query("DELETE FROM currencies WHERE code == :code")
    suspend fun deleteByCode(code: String)
}
