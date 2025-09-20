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

    private val mCourseString = MutableStateFlow("")
    val vCourseString: StateFlow<String> = mCourseString

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
    fun addCourse(department: String, number: String, location: String) {
        if(department != "" &&
            number != "" &&
            location != "")
        {
            if(number.toIntOrNull() != null)
            {
                val newCourse = Course(department, number.toInt(), location)
                mCourseList.value = mCourseList.value + newCourse
                // This resets the text fields
                mDepartmentInput.value = ""
                mNumberInput.value = ""
                mLocationInput.value = ""
            }
            else{
                mError.value = "Invalid course number!"
            }
        }
        else{
            mError.value = "Missing course information!"
        }
    }

//    // This is called when an error occurs
//    fun showError(error: String) {
//        mError.value = error
//    }

    // This is called when the deleteCourse button is called with the proper parameters.
    fun deleteCourse(course: Course?)
    {
        if(course == null)
        {
            mError.value = "No selected course to delete!"
        }
        else
        {
            val tempList = mCourseList.value.toMutableList()
            tempList.remove(course)
            mCourseList.value = tempList
            mSelectedCourse.value = null
            mError.value = "Course deleted!"
            mCourseString.value = ""
        }
    }

    fun courseToString(course: Course?)
    {
        if(course == null)
        {
            mCourseString.value = ""
        }
        else
        {
            mCourseString.value = course.department + " " + course.number + " " + course.location
        }
    }
}