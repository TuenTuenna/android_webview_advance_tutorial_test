package com.example.android_webview_tutorial_test

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.example.android_webview_tutorial_test.MainActivity.Companion.TAG


// 자바스크립트와 통시하는 메소드
/** Instantiate the interface and set the context  */
class MyWebAppInterface(private val mContext: Context, private val webViewInterface: MyWebViewInterface) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(message: String) {
        Log.d(TAG, "showToast: $message")
//        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
        Utils.createAlert(message, mContext, ALERT_TYPE.JS_TO_NATIVE, webViewInterface).show()
    }

    // 얼럿 빌더로 만들기
    // 디자인 패턴 빌더 패턴 - 자기 자신의 인스턴스 반환
    private fun createAlert(title: String, mContext: Context) :AlertDialog.Builder {

        val builder = AlertDialog.Builder(mContext)

//        builder.setTitle()
//        builder.setPositiveButton()
        // apply 는 하나의 객체에 여러개의 메소드를 실행 할때 사용 할 수 있다.
        builder.apply {
            setTitle(title)
            setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, id ->
                    // User clicked OK button
                    Log.d(TAG, "createAlert: 확인")
                    Toast.makeText(mContext, "확인 버튼 클릭", Toast.LENGTH_SHORT).show()
                })
            setNegativeButton("닫기",
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                    Log.d(TAG, "createAlert: 닫기")
                    Toast.makeText(mContext, "닫기 버튼 클릭", Toast.LENGTH_SHORT).show()
                })
        }
        // Set other dialog properties

        // Create the AlertDialog
        builder.create()
        return builder
    }

}
