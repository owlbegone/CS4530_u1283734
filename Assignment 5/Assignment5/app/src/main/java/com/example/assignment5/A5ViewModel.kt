package com.example.assignment5

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class A5ViewModel(private val repository: GravityRepository) : ViewModel() {
    val gravReading = repository.getGravFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GravityReading(0f, 0f, 0f)
        )
    private val xMutable = MutableStateFlow(155f)
    val xReadOnly : StateFlow<Float> = xMutable
    private val yMutable = MutableStateFlow(400f)
    val yReadOnly : StateFlow<Float> = yMutable

    private val xVelocityMutable = MutableStateFlow(0f)
    val xVelocityReadOnly : StateFlow<Float> = xVelocityMutable

    private val yVelocityMutable = MutableStateFlow(0f)
    val yVelocityReadOnly : StateFlow<Float> = yVelocityMutable

    private var currentTime = System.currentTimeMillis()

    private val xBoundMutable = MutableStateFlow(360f)
    val xBoundReadOnly : StateFlow<Float> = xBoundMutable

    private val yBoundMutable = MutableStateFlow(800f)
    val yBoundReadOnly : StateFlow<Float> = yBoundMutable

    fun updatePos() {
        val newTime = System.currentTimeMillis()
        val deltaTime = (newTime - currentTime)/1000f
        val grav = gravReading.value

        val scale = 8

        val xVelocityChange = - deltaTime * scale * grav.x
        xVelocityMutable.value += xVelocityChange
        var newX = xMutable.value + (deltaTime * scale * xVelocityReadOnly.value)
        if (newX < 0) {
            newX = 0f
            xVelocityMutable.value = 0f
        }
        else if (newX > xBoundReadOnly.value - 50)
        {
            newX = xBoundReadOnly.value - 50
            xVelocityMutable.value = 0f
        }
        xMutable.value = newX

        val yVelocityChange = deltaTime * scale * grav.y
        yVelocityMutable.value += yVelocityChange
        var newY = yMutable.value + (deltaTime * scale * yVelocityReadOnly.value)
        if (newY < 0)
        {
            newY = 0f
            yVelocityMutable.value = 0f
        }
        else if (newY > yBoundReadOnly.value- 50)
        {
            newY = yBoundReadOnly.value - 50
            yVelocityMutable.value = 0f
        }
        yMutable.value = newY

        currentTime = newTime
    }

    fun updateBounds(x : Float, y : Float)
    {
        xBoundMutable.value = x
        yBoundMutable.value = y
    }

    fun enableSensor() {
        repository.enableSensor()
    }

    fun disableSensor() {
        repository.disableSensor()
    }
}

object GravViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            // Gemini helped with this part, as the demo code was breaking.
            val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]!!

            // 2. Create the SensorManager.
            val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager

            // 3. Create the GravityRepository.
            val gravityRepository = GravityRepository(sensorManager)

            // 4. Create the A5ViewModel with the repository.
            A5ViewModel(gravityRepository)
        }
    }
}