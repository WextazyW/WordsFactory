package com.example.wordsfactory.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.wordsfactory.R


class VideoFragment : Fragment() {

    private lateinit var webViewVideo: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)

        webViewVideo = view.findViewById(R.id.webViewVideo)
        webViewVideo.webViewClient = WebViewClient()
        webViewVideo.settings.javaScriptEnabled = true
        webViewVideo.loadUrl("https://learnenglish.britishcouncil.org/general-english/video-zone")

        return view
    }


}