package com.coolreecedev.styledpractice

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

/**
 * A simple [Fragment] subclass.
 */
class HowItWorksFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        playVideo()
        return inflater.inflate(R.layout.fragment_how_it_works, container, false)
    }



//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
//    private fun playVideo() {
//        val url = "https://youtu.be/Ehzuq42wB88" // your URL here
//        val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
//            setAudioAttributes(
//                AudioAttributes.Builder()
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .build()
//            )
//            setDataSource(url)
//            prepare() // might take long! (for buffering, etc)
//            start()
//        }
//    }

}
