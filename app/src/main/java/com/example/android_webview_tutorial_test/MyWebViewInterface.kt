package com.example.android_webview_tutorial_test

typealias WEB_ALERT_RESPONSE = MyWebViewInterface.ALERT_RESPONSE

interface MyWebViewInterface {

    enum class ALERT_RESPONSE {
        CONFIRM, DISMISS
    }

    // 자바스크립트 알림
    fun jsAlertAction(response: WEB_ALERT_RESPONSE)

    // 자바스크립트 -> 네이티브
    fun jsToNativeAlertAction(response: WEB_ALERT_RESPONSE)

}


