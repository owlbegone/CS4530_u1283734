package com.example.homework2
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThisViewModel : ViewModel() {
    // NOTE: Gemini inline code completion assisted in filling in the following StateFlows, approximately after the first two.
    // It's really hard to ignore those code completion prompts haha. I turned them off after.
    // Data about the course list; these variables are named m<variable name> and v<variable name>
    // to differentiate between which ones are mutable and which ones are available to the view.

    // Course List
    private val mCourseList = MutableStateFlow<List<Course>>(emptyList())
    val vCourseList: StateFlow<List<Course>> = mCourseList

    // Selected Course
    private val mSelectedCourse = MutableStateFlow<Course?>(null)
    val vSelectedCourse: StateFlow<Course?> = mSelectedCourse

    // Department Input
    private val mDepartmentInput = MutableStateFlow("")
    val vDepartmentInput: StateFlow<String> = mDepartmentInput

    // Number INput
    private val mNumberInput = MutableStateFlow("")
    val vNumberInput: StateFlow<String> = mNumberInput

    // Location Input
    private val mLocationInput = MutableStateFlow("")
    val vLocationInput: StateFlow<String> = mLocationInput

    // Error Message
    private val mError = MutableStateFlow("")
    val vError: StateFlow<String> = mError

    // This is called when the text field for department changes
    fun onDepartmentChange(input: String) {
        mDepartmentInput.value = input
    }

    // This is called when the text field for number changes
    fun onNumberChange(input: String) {
        mNumberInput.value = input
    }

    // This is called when the text field for location changes
    fun onLocationChange(input: String) {
        mLocationInput.value = input

    }

    // This is called when a course is selected from the LazyColumn
    fun selectCourse(course: Course) {
        mSelectedCourse.value = course
    }

    // This is called when the addCourse button is called with proper parameters
    fun addCourse(department: String, number: Int, location: String) {
        val newCourse = Course(department, number, location)
        mCourseList.value = mCourseList.value + newCourse
        // This resets the text fields
        mDepartmentInput.value = ""
        mNumberInput.value = ""
        mLocationInput.value = ""
    }

    // This is called when an error occurs
    fun showError(error: String) {
        mError.value = error
    }

    // This is called when the deleteCourse button is called with the proper parameters.
    fun deleteCourse(course: Course?)
    {
        val tempList = mCourseList.value.toMutableList()
        tempList.remove(course)
        mCourseList.value = tempList
        if(mSelectedCourse.value?.department == course?.department &&
            mSelectedCourse.value?.number == course?.number &&
            mSelectedCourse.value?.location == course?.location)
        {
            mSelectedCourse.value = null
        }
    }
}