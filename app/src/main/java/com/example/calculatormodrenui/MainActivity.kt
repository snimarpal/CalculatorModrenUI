package com.example.calculatormodrenui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.StringBuilder
import android.util.Log

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var textViewInput: TextView
    private lateinit var textViewResult: TextView

    var operandList = mutableListOf<Double>()
    var operatorList = mutableListOf<String>()
    var input : String? = null
    var text : String? = null

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
            R.id.buttonAC, R.id.buttonCE, R.id.buttonMod
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
            R.id.buttonMod -> setOperator("%")
            R.id.buttonEquals -> calculateResult()

        }
    }

    private fun appendInput(value: String) {
        if (input == null) {
            input = value
            if (text == null) {
                text = value
            } else {
                text += value
            }
        } else {
            input += value
            text = textViewInput.text.toString() + value
        }
        textViewInput.text = text
        textViewResult.text = null

        // Print debug information
        println("input: $input")
        println("text: $text")
        println("operandList: $operandList")
        println("operatorList: $operatorList")
    }

    private fun clearInput() {
        text = null
        input = null
        operandList.clear()
        operatorList.clear()
        textViewInput.text = text
        textViewResult.text = null
    }

    private fun deleteLastInput() {
        if (input != null) {
            text = text?.dropLast(1)
            if (input!!.count() == 1) {
                input = null
            } else {
                input = input?.dropLast(1)
            }
            if (input == null && operandList.isNotEmpty()) {
                operandList [operandList.lastIndex] /= 10.0
                if (operandList.last() == 0.0) {
                    operandList.removeLast()
                }
            } else {
                operandList [operandList.lastIndex] = input!!.toDouble()
            }
        } else {
            text = text?.dropLast(1)
            if (operatorList.isNotEmpty()) {
                operatorList.removeLast()
            }
        }
        textViewInput.text = text
        textViewResult.text = null
    }

    private fun setOperator(operator: String) {
        if (input != null) {
            operandList.add(input!!.toDouble())
            operatorList.add(operator)
            text += operator
            textViewInput.text = text
            input = null
        } else {
            textViewResult.text = "invalid"
        }
        textViewResult.text = null
        println("input : " + input)
        println("text : "+text)
        println("operandList : "+operandList)
        println("operatorList : "+operatorList)
    }

    private fun calculateResult() {
        var result:Double = 0.0
        if (operandList.isNotEmpty()) {
            result = operandList[0]
            for (i in 1 until (operandList.count())) {
                when (operatorList[i - 1]) {
                    "+" -> result += operatorList[i+1].toDouble()
                    "-" -> result -= operatorList[i+1].toDouble()
                    "*" -> result *= operatorList[i+1].toDouble()
                    "/" -> result /= operatorList[i+1].toDouble()
                    "%" -> result %= operatorList[i+1].toDouble()
                }
            }
        }
        textViewResult.text = result.toString()
    }
}