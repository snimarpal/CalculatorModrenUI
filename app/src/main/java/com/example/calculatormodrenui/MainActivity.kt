package com.example.calculatormodrenui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var textViewInput: TextView
    private lateinit var textViewResult: TextView

    private var input: StringBuilder = StringBuilder()
    private var num1: Double = 0.0
    private var num2: Double = 0.0
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the TextViews by their IDs
        textViewInput = findViewById(R.id.textViewInput)
        textViewResult = findViewById(R.id.textViewResult)

        // Set click listeners for buttons
        val buttons = arrayOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonDot, R.id.buttonEquals,
            R.id.buttonAdd, R.id.buttonSubtract, R.id.buttonMultiply, R.id.buttonDivide,
            R.id.buttonAC, R.id.buttonCE
        )

        for (buttonId in buttons) {
            findViewById<Button>(buttonId).setOnClickListener(this)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9, R.id.buttonDot -> {
                val buttonText = (view as Button).text
                appendInput(buttonText.toString())
            }

            R.id.buttonAC -> clearInput()
            R.id.buttonCE -> deleteLastInput()
            R.id.buttonDivide -> setOperator("/")
            R.id.buttonMultiply -> setOperator("*")
            R.id.buttonSubtract -> setOperator("-")
            R.id.buttonAdd -> setOperator("+")
            R.id.buttonEquals -> calculateResult()

        }
    }

    fun appendInput(value: String) {
        input.append(value)
        textViewInput.text = input.toString()
    }

    fun clearInput() {
        input.clear()
        num1 = 0.0
        num2 = 0.0
        operator = null
        textViewInput.text = ""
        textViewResult.text = ""
    }

    fun deleteLastInput() {
        if (input.isNotEmpty()) {
            input.deleteAt(input.length - 1)
            textViewInput.text = input.toString()
        }
    }

    fun setOperator(operator: String) {
        if (input.isNotEmpty()) {
            num1 = input.toString().toDouble()
            this.operator = operator
            input.append(operator)
            textViewInput.text = input.toString()
        }
    }

    fun calculateResult() {
        if (input.isNotEmpty() && operator != null) {
            num2 = input.toString().toDouble()
            var result = 0.0

            when (operator) {
                "+" -> result = num1 + num2
                "-" -> result = num1 - num2
                "*" -> result = num1 * num2
                "/" -> {
                    if (num2 != 0.0) {
                        result = num1 / num2
                    } else {
                        textViewResult.text = "Error: Division by Zero"
                        return
                    }
                }
            }

            textViewResult.text = result.toString()
            clearInput()
            num1 = result
        }
    }
}