package com.example.android_webview_tutorial_test

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast

enum class ALERT_TYPE {
    JS_ALERT, JS_TO_NATIVE
}
object Utils {
    @JvmStatic

    fun createAlert(title: String, mContext: Context, type : ALERT_TYPE, webViewInterface: MyWebViewInterface) : AlertDialog.Builder {
        val builder = AlertDialog.Builder(mContext)
        // apply 는 하나의 객체에 여러개의 메소드를 실행 할때 사용 할 수 있다.
        builder.apply {
            setTitle(title)
            setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    // User clicked OK button
                    Log.d(MainActivity.TAG, "createAlert: 확인")
                    Toast.makeText(mContext, "확인 버튼 클릭", Toast.LENGTH_SHORT).show()
                    when (type) {
                        ALERT_TYPE.JS_ALERT ->
                            webViewInterface.jsAlertAction(WEB_ALERT_RESPONSE.CONFIRM)
                        ALERT_TYPE.JS_TO_NATIVE ->
                            webViewInterface.jsToNativeAlertAction(WEB_ALERT_RESPONSE.CONFIRM)
                    }
                })
            setNegativeButton("닫기",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                    Log.d(MainActivity.TAG, "createAlert: 닫기")
                    Toast.makeText(mContext, "닫기 버튼 클릭", Toast.LENGTH_SHORT).show()
                    when (type) {
                        ALERT_TYPE.JS_ALERT ->
                            webViewInterface.jsAlertAction(WEB_ALERT_RESPONSE.DISMISS)
                        ALERT_TYPE.JS_TO_NATIVE ->
                            webViewInterface.jsToNativeAlertAction(WEB_ALERT_RESPONSE.DISMISS)
                    }
                })
        }
        // Set other dialog properties

        // Create the AlertDialog
        builder.create()
        return builder
    }

}

