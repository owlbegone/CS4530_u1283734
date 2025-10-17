package com.example.homework2

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Database(entities= [CourseData::class], version = 1, exportSchema = false)
abstract class CourseDatabase : RoomDatabase(){
    abstract fun courseDao(): CourseDAO
}

@Dao
interface CourseDAO {
    @Insert
    suspend fun addCourseData(data: CourseData)
    @Query("SELECT * from courses ORDER BY number DESC LIMIT 1")
    fun specificCourse() : Flow<CourseData?>
    @Query("SELECT * from courses ORDER BY department DESC")
    fun allCourses() : Flow<List<CourseData>>
    @Query("DELETE FROM courses WHERE department = :dept AND number = :number AND location = :location")
    suspend fun deleteCourse(dept: String, number: Int, location: String)
}