package com.example.top10downloaded

class FeedEntry {
    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var imageUrl: String = ""
    var content: String = ""

    override fun toString(): String {
        return """
            name: $name
            artist: $artist
            releaseDate: $releaseDate
            content: $content
            imageUrl: $imageUrl
        """.trimIndent()
    }
}