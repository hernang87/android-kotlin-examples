package com.example.top10downloaded

import android.view.View
import android.widget.TextView

class ViewHolder(v: View) {
    val tvName: TextView = v.findViewById(R.id.tvName)
    val tvArtist: TextView = v.findViewById(R.id.tvArtist)
    val tvContent: TextView = v.findViewById(R.id.tvContent)
}