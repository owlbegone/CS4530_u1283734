package com.example.homework4_2

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Entity(tableName="fact")
data class FactData(val text: String)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Database(entities= [FactData::class], version = 1, exportSchema = false)

abstract class FactDatabase : RoomDatabase() {
    abstract fun factDao(): FactDAO
}

@Dao
interface FactDAO {
    @Insert
    suspend fun addFactData(fact: FactData)
    @Query("SELECT * from fact")
    fun allFacts() : Flow<List<FactData>>
}