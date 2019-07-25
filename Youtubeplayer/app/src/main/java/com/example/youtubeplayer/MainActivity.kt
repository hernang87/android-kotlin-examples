package com.example.youtubeplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlaySingleVideo.setOnClickListener(this)
        btnStandaloneSubMenu.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val intent = when(view.id) {
            R.id.btnPlaySingleVideo -> Intent(this, YouTubeActivity::class.java)
            R.id.btnStandaloneSubMenu -> Intent(this, StandaloneActivity::class.java)
            else -> throw IllegalArgumentException("Undefined button")
        }

        startActivity(intent)
    }
}
