package com.emikhalets.convertapp.core.database.table_currencies

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emikhalets.convertapp.domain.model.CurrencyModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Entity(tableName = "currencies")
data class CurrencyDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "code")
    val code: String,
) {

    companion object {

        fun CurrencyModel.toDb(): CurrencyDb {
            return CurrencyDb(id, code)
        }

        fun List<CurrencyModel>.toDbList(): List<CurrencyDb> {
            return map { it.toDb() }
        }

        fun Flow<List<CurrencyModel>>.toDbFlow(): Flow<List<CurrencyDb>> {
            return map { it.toDbList() }
        }

        fun CurrencyDb.toModel(): CurrencyModel {
            return CurrencyModel(id, code)
        }

        fun List<CurrencyDb>.toModelList(): List<CurrencyModel> {
            return map { it.toModel() }
        }

        fun Flow<List<CurrencyDb>>.toModelFlow(): Flow<List<CurrencyModel>> {
            return map { it.toModelList() }
        }
    }
}
