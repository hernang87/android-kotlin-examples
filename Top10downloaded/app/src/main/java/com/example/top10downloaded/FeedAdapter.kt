package com.example.top10downloaded

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class FeedAdapter(context: Context, private val resource: Int, private val albums: List<FeedEntry>) : ArrayAdapter<FeedEntry>(context, resource) {
    private val inflater =  LayoutInflater.from(context)

    override fun getCount(): Int {
        return albums.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val vh: ViewHolder
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        val currentAlbum = albums[position]
        vh.tvName.text = currentAlbum.name
        vh.tvArtist.text = currentAlbum.artist

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            vh.tvContent.text = Html.fromHtml(currentAlbum.content, Html.FROM_HTML_MODE_COMPACT)
        } else {
            vh.tvContent.text = Html.fromHtml(currentAlbum.content)
        }

        return view
    }
}