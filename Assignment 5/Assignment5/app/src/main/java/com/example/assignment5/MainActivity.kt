package com.example.assignment5

import android.R.attr.maxHeight
import android.R.attr.maxWidth
import android.R.attr.minHeight
import android.R.attr.minWidth
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //val myVM: A5ViewModel by viewModels { GravViewModelProvider.Factory }
            // The composable function here
            MarbleScreen()
        }
    }

    override fun onResume() {
        super.onResume()

    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MarbleScreen(vm: A5ViewModel = viewModel(factory = GravViewModelProvider.Factory)) {
    val gravReading by vm.gravReading.collectAsStateWithLifecycle()
    val xPos by vm.xReadOnly.collectAsState()
    val yPos by vm.yReadOnly.collectAsState()

    val temp = gravReading

//    val oldX by remember {mutableStateOf(0f)}
//    val oldY by remember {mutableStateOf(0f)}
    BoxWithConstraints ( modifier = Modifier
        .background(color = Color(0xffe0ddd5))
        .fillMaxSize()) {

        //val minW = 0f
        val maxW = maxWidth.value
        //val minH = 0f
        val maxH = maxHeight.value
        vm.updateBounds(maxW, maxH)

        vm.updatePos()

        Box(Modifier
            .offset((xPos + 10).dp, (yPos + 10).dp)
            .size(50.dp, 50.dp)
            .background(color = Color(0xff4a3418), CircleShape)
            .innerShadow(CircleShape, Shadow(radius = 30.dp, spread = 10.dp, color = Color(0xffe0ddd5)))
            )

        Box(Modifier
            .offset(xPos.dp, yPos.dp)
            .size(50.dp, 50.dp)
            //.dropShadow(CircleShape, Shadow(25.dp, color = Color(0xff4a3418)))
            //.dropShadow(CircleShape, Shadow(12.dp, color = Color(0xFF2C1606)))
            .background(color = Color(0xffed3232), CircleShape)
            .innerShadow(CircleShape, Shadow(radius = 20.dp, spread = 5.dp, color = Color(0xffb51212)))
            //.innerShadow(CircleShape, Shadow(radius = 5.dp, spread = 2.dp, color = Color(0xff660707)))
        )
        Box(Modifier
            .offset((xPos +13).dp, (yPos + 15).dp)
            .size(10.dp, 10.dp)
            .background(color = Color(0xFFF3B3C2), CircleShape)
            .innerShadow(CircleShape, Shadow(radius = 4.dp, spread = 1.dp, color = Color(0xffed3232))))
        Text(
            text = "x: $xPos, y: $yPos",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp
        )
    }
}