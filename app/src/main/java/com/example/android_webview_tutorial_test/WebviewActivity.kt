package com.example.android_webview_tutorial_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class WebviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_webview)

        // 자체에 웹뷰 적용
        val myWebView = WebView(this)
        setContentView(myWebView)
        myWebView.loadUrl(Constants.DEV_JEONG_DAE_RI_URL)
    }
}
