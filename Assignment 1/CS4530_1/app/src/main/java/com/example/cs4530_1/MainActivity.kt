package com.example.cs4530_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // These variables each represent one of the five buttons.
        val buttonOne = findViewById<Button>(R.id.button_1)
        val buttonTwo = findViewById<Button>(R.id.button_2)
        val buttonThree = findViewById<Button>(R.id.button_3)
        val buttonFour = findViewById<Button>(R.id.button_4)
        val buttonFive = findViewById<Button>(R.id.button_5)

        // This variable sets up the intent for the second activity, which is called Second.
        val intentOne = Intent(getApplicationContext(), Second::class.java)

        // Set up OnClick listeners for all buttons, then call the function addButtonText.
        buttonOne.setOnClickListener {
            addButtonText(buttonOne.text, intentOne)
        }
        buttonTwo.setOnClickListener {
            addButtonText(buttonTwo.text, intentOne)
        }
        buttonThree.setOnClickListener {
            addButtonText(buttonThree.text, intentOne)
        }
        buttonFour.setOnClickListener {
            addButtonText(buttonFour.text, intentOne)
        }
        buttonFive.setOnClickListener {
            addButtonText(buttonFive.text, intentOne)
        }
    }

    /***
     * This is a helper function to add the button text to the new intent based on which button
     * was pressed. After, it starts the activity.
     */
    fun addButtonText(text: CharSequence, thisIntent: Intent) {
        thisIntent.putExtra("ButtonText", text)
        startActivity(thisIntent)
        return
    }
}