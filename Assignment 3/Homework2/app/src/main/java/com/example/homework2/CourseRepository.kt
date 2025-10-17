package com.example.homework2

import android.util.Log
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.random.Random

class CourseRepository(val scope: CoroutineScope,
    private val dao: CourseDAO) {
    val specificCourse: Flow<CourseData?> = dao.specificCourse()
    val allCourses = dao.allCourses()

    fun addCourse(dept: String, number: Int, location: String)
    {
        scope.launch {
            Log.e("REPO", "AAAAAAAA")
            dao.addCourseData(CourseData(dept, number, location))
        }
    }
    fun deleteCourse(dept: String, number: Int, location: String)
    {
        scope.launch {
            Log.e("REPO", "DELETING")
            dao.deleteCourse(dept, number, location)
        }
    }
}