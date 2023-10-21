package com.emikhalets.convertapp.core.database.table_exchanges

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangesDao {

    @Insert
    suspend fun insert(item: ExchangeDb): Long

    @Insert
    suspend fun insert(list: List<ExchangeDb>): List<Long>

    @Update
    suspend fun update(item: ExchangeDb): Int

    @Update
    suspend fun update(list: List<ExchangeDb>): Int

    @Delete
    suspend fun delete(item: ExchangeDb): Int

    @Query("DELETE FROM exchanges")
    suspend fun drop(): Int

    @Query("SELECT * FROM exchanges")
    suspend fun getAll(): List<ExchangeDb>

    @Query("SELECT * FROM exchanges")
    fun getAllFlow(): Flow<List<ExchangeDb>>

    @Query("SELECT * FROM exchanges WHERE id = :id")
    suspend fun getItem(id: Long): ExchangeDb

    @Query("SELECT * FROM exchanges WHERE id = :id")
    fun getItemFlow(id: Long): Flow<ExchangeDb>

    @Query(
        "DELETE FROM exchanges WHERE " +
                "main LIKE '%' || :code || '%' OR " +
                "sub LIKE '%' || :code || '%'"
    )
    suspend fun deleteByCode(code: String)
}
