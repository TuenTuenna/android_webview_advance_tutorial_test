package com.example.android_webview_tutorial_test

import android.content.Context
import android.util.Log
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast

// 커스텀 웹 크롬 클라이언트
class MyWebClient(private val mContext: Context, private val webViewInterface: MyWebViewInterface) : WebChromeClient() {

    // 자바스크립트 alert 이 들어왔을 때
    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
//        return super.onJsAlert(view, url, message, result)
        Log.d(MainActivity.TAG, "onJsAlert: $message")
//        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show()
        Utils.createAlert(message ?: "빈 값", mContext, ALERT_TYPE.JS_ALERT, webViewInterface).show()
        result?.cancel()
        return true
    }


}
