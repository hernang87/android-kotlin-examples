package com.example.top10downloaded

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseAlbums {
    private val TAG = "ParseAlbums"
    val albums = ArrayList<FeedEntry>()

    fun parse(xmlData: String): Boolean {
        var status = true
        var inEntry = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true

            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = FeedEntry()

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val tagName = xpp.name?.toLowerCase()
                when(eventType) {
                    XmlPullParser.START_TAG -> {
                        Log.d(TAG, "parse: started for tag $tagName")
                        if (tagName == "entry") {
                            inEntry = true
                        }
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    XmlPullParser.END_TAG -> {
                        Log.d(TAG, "parse: started for tag $tagName")
                        if (inEntry) {
                            when (tagName) {
                                "entry" -> {
                                    albums.add(currentRecord)
                                    inEntry = false
                                    currentRecord = FeedEntry()
                                }
                                "name" -> currentRecord.name = textValue
                                "artist" -> currentRecord.artist = textValue
                                "content" -> currentRecord.content = textValue
                                "releasedate" -> currentRecord.releaseDate = textValue
                                "image" -> currentRecord.imageUrl = textValue
                            }
                        }
                    }
                }
                eventType = xpp.next()
            }
        } catch(e: Exception) {
            e.printStackTrace()
            status = false
        }

        return status
    }
}