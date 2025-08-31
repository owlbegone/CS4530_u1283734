package com.example.cs4530_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Second : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Get the text of the button from the main activity from the intent using the key "ButtonText".
        val buttonText = getIntent().getStringExtra("ButtonText")
        // Set the text in the text box.
        val textBox = findViewById<TextView>(R.id.textView)
        textBox.text = buttonText

        // Save the back button as a variable
        val backButton = findViewById<Button>(R.id.back_button)
        // Set up a listener for the back button; when pressed, the main activity will start again.
        backButton.setOnClickListener {
            val thisIntent = Intent(getApplicationContext(), MainActivity::class.java)
            startActivity(thisIntent)
        }
    }
}