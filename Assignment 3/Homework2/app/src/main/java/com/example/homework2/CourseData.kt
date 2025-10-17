package com.example.homework2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="courses")
data class CourseData(var department: String,
                      var number: Int,
                      var location: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}