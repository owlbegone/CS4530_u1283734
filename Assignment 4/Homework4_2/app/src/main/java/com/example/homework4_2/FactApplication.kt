package com.example.homework4_2

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob

class FactApplication : Application() {
    val scope = CoroutineScope(SupervisorJob())
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            FactDatabase::class.java,
            "fact"
        ).build()
    }
    val factRepository by lazy { FactRepository(scope, db.factDao())}
}