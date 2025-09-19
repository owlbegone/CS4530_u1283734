package com.example.homework2

import android.os.Bundle
// I tried using Toast to display error messages. I couldn't get it to work :(
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val vm:ThisViewModel = viewModel()
                thisView(vm)
            }
        }
    }
}

// This is the main view of the project.
@Composable
fun thisView(myVM: ThisViewModel)
{
    // Stores all the values that we need from the viewModel
    val courses by myVM.vCourseList.collectAsState()
    val selectedCourse by myVM.vSelectedCourse.collectAsState()
    val errorMessage by myVM.vError.collectAsState()
    val departmentText by myVM.vDepartmentInput.collectAsState()
    val numberText by myVM.vNumberInput.collectAsState()
    val locationText by myVM.vLocationInput.collectAsState()
    var selectedCourseToString = ""
    // If the selected course is not null, convert it to a string to display
    if(selectedCourse != null)
    {
        selectedCourseToString = selectedCourse?.department + " " + selectedCourse?.number + " " + selectedCourse?.location
    }
    // Otherwise display 'no course selected.'
    else{
        selectedCourseToString = "No course selected."
    }

    // Text/Text fields are all in one column:
    Column(
        modifier = Modifier.fillMaxSize().padding(200.dp, 50.dp, 50.dp, 50.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    )
    {
        // Text that displays a greeting and instructions
        Text(
            text = "Welcome! To add a course, add the course details below.",
            fontSize = 15.sp)
        // Text that displays an error message based on user input
        Text(
            text = errorMessage,
            fontSize = 10.sp
        )
        // First text field for department
        OutlinedTextField(
            value = departmentText,
            onValueChange = {myVM.onDepartmentChange(it)},
            label = {Text("Course Department:")}
        )
        // Second text field for the course number
        OutlinedTextField(
            value = numberText,
            onValueChange = {myVM.onNumberChange(it)},
            label = {Text("Course Number:")}
        )
        // Third text field for the location
        OutlinedTextField(
            value = locationText,
            onValueChange = {myVM.onLocationChange(it)},
            label = {Text("Course Location:")}
        )
        // This is the add course button; if any of the fields are empty, display "missing information
        // in the error text.
        Button(
            onClick = {
                if(departmentText != "" &&
                    numberText != "" &&
                    locationText != "")
                {
                    if(numberText.toIntOrNull() != null)
                    {
                        // Otherwise, add a course.
                        myVM.addCourse(departmentText, numberText.toInt(), locationText)
                    }
                    else{
                        myVM.showError("Not a valid course number!")
                    }
                }
                else{
                    myVM.showError("Missing information!")
                }
            })
        {
            Text(text = "Add Course")
        }
        // This text box and the next display a helpful little message and the currently selected course
        Text(
            text = "Currently selected course: ",
            fontSize = 15.sp)
        Text(
            text = selectedCourseToString,
            fontSize = 30.sp)
        // This is the delete button; it displays an error instead if there is no selected course.
        Button(
            onClick = {
                if(selectedCourse != null)
                {
                    myVM.deleteCourse(selectedCourse)
                }
                else
                {
                    myVM.showError("No selected course to delete.")
                }
                }
        )
        {
            Text(text = "Delete Selected Course")
        }
    }

    // This is the LazyColumn that displays the courses
    LazyColumn(
        modifier = Modifier.padding(50.dp, 50.dp, 200.dp, 50.dp),
        horizontalAlignment = Alignment.Start) {
        if(courses.isEmpty())
        {
            // Gemini assisted with figuring out why this was not correct when it was just
            // Text("Nothing here yet!") rather than wrapped in an item {}.
            item{
                Text("Nothing here yet!")
            }
        }
        else
        {
            items(items = courses, itemContent = { item ->
                Button(onClick = { myVM.selectCourse(item)}) {
                    Text(item.department + " " + item.number, style = TextStyle(fontSize = 20.sp))
                }
            })
        }
    }
}