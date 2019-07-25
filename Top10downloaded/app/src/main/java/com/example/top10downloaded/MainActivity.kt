package com.example.top10downloaded

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val url = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
    private var limit = 10

    private var download: DownloadData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadUrl(url.format(limit))
    }

    private fun downloadUrl(feedUrl: String) {
        download = DownloadData(this, xmlListView)
        download?.execute(feedUrl)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menuTop10, R.id.menuTop25 -> {
                val checked = item.isChecked

                if (!checked) {
                    item.isChecked = true
                    limit = 25
                }

                return checked
            }
            else -> null
        } ?: return super.onOptionsItemSelected(item)

        downloadUrl(url.format(limit))
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()

        download?.cancel(true)
    }

    companion object {
        private class DownloadData(context: Context, listView: ListView) : AsyncTask<String, Void, String>() {
            private val TAG: String = "DownloadData"

            var propContext: Context by Delegates.notNull()
            var propListView: ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }

            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                val parseAlbums = ParseAlbums()
                parseAlbums.parse(result)

                val adapter = FeedAdapter(propContext, R.layout.list_record, parseAlbums.albums)
                propListView.adapter = adapter
            }

            override fun doInBackground(vararg url: String?): String {
//                Log.d(TAG, "doInBackground starts with ${url[0]}")
                val rssFeed = downloadXML(url[0])

                if (rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: Error downloading")
                }

                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                return URL(urlPath).readText()
            }


        }
    }
}
