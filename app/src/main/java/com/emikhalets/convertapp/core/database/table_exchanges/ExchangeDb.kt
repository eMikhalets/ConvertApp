package com.emikhalets.convertapp.core.database.table_exchanges

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.convertapp.domain.model.ExchangeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Entity(tableName = "exchanges")
data class ExchangeDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "main")
    val main: String,
    @ColumnInfo(name = "sub")
    val sub: String,
    @ColumnInfo(name = "value")
    val value: Double,
    @ColumnInfo(name = "date")
    val date: Long,
) {

    companion object {

        fun ExchangeModel.toDb(): ExchangeDb {
            return ExchangeDb(id, main, sub, value, date)
        }

        fun List<ExchangeModel>.toDbList(): List<ExchangeDb> {
            return map { it.toDb() }
        }

        fun Flow<List<ExchangeModel>>.toDbFlow(): Flow<List<ExchangeDb>> {
            return map { it.toDbList() }
        }

        fun ExchangeDb.toModel(): ExchangeModel {
            return ExchangeModel(id, main, sub, value, date)
        }

        fun List<ExchangeDb>.toModelList(): List<ExchangeModel> {
            return map { it.toModel() }
        }

        fun Flow<List<ExchangeDb>>.toModelFlow(): Flow<List<ExchangeModel>> {
            return map { it.toModelList() }
        }
    }
}
