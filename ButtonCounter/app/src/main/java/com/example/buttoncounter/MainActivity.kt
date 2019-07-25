package com.example.buttoncounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Button
import android.widget.TextView

private const val TAG = "MainActivity"
private const val TEXT_CONTENTS = "TEXT_CONTENTS"

class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null
    private var txt: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById<EditText>(R.id.editText)
        val button: Button = findViewById<Button>(R.id.button)
        textView = findViewById<TextView>(R.id.textView)
        textView?.text = ""
        textView?.movementMethod = ScrollingMovementMethod()

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.d(TAG, "onClick")

                textView?.append(editText.text)
                textView?.append("\n")
                editText.text.clear()
            }
        })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")
        textView?.text = savedInstanceState?.getString(TEXT_CONTENTS, "")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")
        outState?.putString(TEXT_CONTENTS, textView?.text.toString())
    }
}
