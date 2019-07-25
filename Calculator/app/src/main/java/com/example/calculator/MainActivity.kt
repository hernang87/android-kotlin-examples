package com.example.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

private const val STATE_PENDING_OPERATION = "PENDING_OPERATION"
private const val STATE_OPERAND1 = "OPERAND1"
private const val STATE_OPERAND1_STORED = "STATE_OPERAND1_STORED"

class MainActivity : AppCompatActivity() {
    private var operand1: Double? = null
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listener = View.OnClickListener { v ->
            val btn = v as Button
            txtNewNumber.append(btn.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()

            try {
                val value = txtNewNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                txtNewNumber.setText("")
            }

            pendingOperation = op
            txtOperation.text = pendingOperation
        }

        buttonDivide.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonEquals.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)

        buttonNeg.setOnClickListener({ _ ->
            val value = txtNewNumber.text.toString()
            if(value.isEmpty()) {
                txtNewNumber.setText("-")
            } else {
                try {
                    val double = value.toDouble() * -1
                    txtNewNumber.setText(double.toString())
                } catch(e: NumberFormatException) {
                    txtNewNumber.setText("")
                }
            }
        })
    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) Double.NaN else operand1!!
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }
        }
        txtResult.setText(operand1.toString())
        txtNewNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(STATE_PENDING_OPERATION, pendingOperation)

        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1!!)
            outState.putBoolean(STATE_OPERAND1_STORED, true)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        operand1 = if (savedInstanceState.getBoolean(STATE_OPERAND1_STORED, false))
            savedInstanceState.getDouble(STATE_OPERAND1)
        else
            null

        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION)
        txtOperation.text = pendingOperation
    }
}
