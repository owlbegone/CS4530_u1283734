package com.example.homework2
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.remove

//private val ThisViewModel.application: CourseApplication

class ThisViewModel(private val repository: CourseRepository) : ViewModel() {
    // NOTE: Gemini inline code completion assisted in filling in the following StateFlows, approximately after the first two.
    // It's really hard to ignore those code completion prompts haha. I turned them off after.
    // Data about the course list; these variables are named m<variable name> and v<variable name>
    // to differentiate between which ones are mutable and which ones are available to the view.

    // Course List
    //private val mCourseList = MutableStateFlow<List<Course>>(emptyList())
    //val vCourseList: StateFlow<List<Course>> = mCourseList

    val courseReadOnly: Flow<List<CourseData?>> = repository.allCourses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    // Selected Course
    private val mSelectedCourse = MutableStateFlow<CourseData?>(null)
    val vSelectedCourse: StateFlow<CourseData?> = mSelectedCourse

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
    fun selectCourse(course: CourseData) {
        mSelectedCourse.value = course
    }

    // This is called when the addCourse button is called with proper parameters
    fun addCourse(departmentText: String, numberText: String, locationText: String) {
        //val newCourse = CourseData(department, number, location)
        //mCourseList.value = mCourseList.value + newCourse
        // This resets the text fields

        if(departmentText != "" &&
            numberText != "" &&
            locationText != "")
        {
            if(numberText.toIntOrNull() != null)
            {
                // Otherwise, add a course.
                repository.addCourse(departmentText, numberText.toInt(), locationText)
                //val newCourse = Course(departmentText, numberText.toInt(), locationText)
                //mCourseList.value = mCourseList.value + newCourse
            }
            else{
                mError.value = "Not a valid course number!"
            }
        }
        else{
            mError.value = "Missing information!"
        }

        //repository.addCourse(department, number, location)

        mDepartmentInput.value = ""
        mNumberInput.value = ""
        mLocationInput.value = ""
    }

    // This is called when an error occurs
//    fun showError(error: String) {
//        mError.value = error
//    }

    // This is called when the deleteCourse button is called with the proper parameters.
    fun deleteCourse(course: CourseData?) {
        if (course != null) {
//            val tempList = mCourseList.value.toMutableList()
//            tempList.remove(course)
//            mCourseList.value = tempList
//            if (mSelectedCourse.value?.department == course?.department &&
//                mSelectedCourse.value?.number == course?.number &&
//                mSelectedCourse.value?.location == course?.location
//            ) {
//                mSelectedCourse.value = null
//            }
            repository.deleteCourse(course.department, course.number, course.location)
            mSelectedCourse.value = null
        } else {
            mError.value = "No course selected!"
        }
    }
}

object CourseViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ThisViewModel(
                //fetches the application singleton
                (this[AndroidViewModelFactory.APPLICATION_KEY]
                        //and then extracts the repository in it
                        as CourseApplication).courseRepository
            )
        }
    }
}