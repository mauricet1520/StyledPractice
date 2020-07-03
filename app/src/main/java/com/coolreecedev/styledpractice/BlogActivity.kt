package com.coolreecedev.styledpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.coolreecedev.styledpractice.util.FileHelper
import kotlinx.android.synthetic.main.activity_faq.*

class BlogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true
        val url = FileHelper.getBlogUrl()
        webView.loadUrl(url)
    }
}
