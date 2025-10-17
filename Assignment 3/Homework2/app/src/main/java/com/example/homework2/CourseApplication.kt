package com.example.homework2

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob

class CourseApplication : Application() {
    val scope = CoroutineScope(SupervisorJob())

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CourseDatabase::class.java,
            "course_database"
        ).build()
    }

    val courseRepository by lazy { CourseRepository(scope, db.courseDao())}
}