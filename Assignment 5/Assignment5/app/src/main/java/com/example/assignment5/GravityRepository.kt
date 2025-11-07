package com.example.assignment5

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class GravityRepository(private val sensorManager: SensorManager)
{
    fun getGravFlow(): Flow<GravityReading> = channelFlow {
        val gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        if (gravity == null) {
            return@channelFlow
        }

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                trySendBlocking(GravityReading(event.values[0], event.values[1], event.values[2]))
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        sensorManager.registerListener(listener, gravity, SensorManager.SENSOR_DELAY_UI)
        awaitClose { sensorManager.unregisterListener(listener)}
    }
}