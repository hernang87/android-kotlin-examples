package com.example.youtubeplayer

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "f7Rn7zlmptc"
const val YOUTUBE_PLAYLIST = "PLm0weckGVyh-ofhyEZrB5r6wYmdLuP7yM"

class YouTubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "YouTubeActivity"
    private val DIALOG_REQUEST_CODE = 1
    private val playerView by lazy { YouTubePlayerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)

        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)

    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
        Log.d(TAG, "onInitializationSuccess")
        Log.d(TAG, "provider is ${provider?.javaClass}")
        Log.d(TAG, "player is ${player?.javaClass}")

        player?.setPlayerStateChangeListener(playerStateChangeListener)
        player?.setPlaybackEventListener(playbackEventListener)

        Toast.makeText(this, "Started correctly", Toast.LENGTH_SHORT).show()

        if(!wasRestored) {
            player?.loadVideo(YOUTUBE_VIDEO_ID)
        }

        player?.play()
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) {
        Log.d(TAG, "onInitializationFailure")

        if (result?.isUserRecoverableError == true) {
            result.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
        } else {
            val errorMessage = "Error initializing app"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object: YouTubePlayer.PlaybackEventListener {
        private val TAG = "PlaybackEventListener"

        override fun onSeekTo(p0: Int) {
            Log.d(TAG, "onSeekTo")
        }

        override fun onBuffering(p0: Boolean) {
            Log.d(TAG, "onBuffering")
        }

        override fun onPlaying() {
            Log.d(TAG, "onPlaying")
        }

        override fun onStopped() {
            Log.d(TAG, "onStopped")
        }

        override fun onPaused() {
            Log.d(TAG, "onPaused")
        }
    }

    private val playerStateChangeListener = object: YouTubePlayer.PlayerStateChangeListener {
        private val TAG = "PlayerStateChange"

        override fun onAdStarted() {
            Log.d(TAG, "onAdStarted")
        }

        override fun onLoading() {
            Log.d(TAG, "onLoading")
        }

        override fun onVideoStarted() {
            Log.d(TAG, "onVideoStarted")
        }

        override fun onLoaded(p0: String?) {
            Log.d(TAG, "onLoaded")
        }

        override fun onVideoEnded() {
            Log.d(TAG, "onVideoEnded")
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
            Log.d(TAG, "onError")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "onActivityResult requestCode: $requestCode resultCode: $resultCode ")

        if(requestCode == DIALOG_REQUEST_CODE) {
            Log.d(TAG, intent?.toString())
            Log.d(TAG, intent?.extras.toString())
            playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
        }
    }
}
