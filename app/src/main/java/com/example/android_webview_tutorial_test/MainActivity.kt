package com.example.android_webview_tutorial_test

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), MyWebViewInterface {

    companion object {
        const val TAG = "로그"
    }

    lateinit var myWebView : WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        configureWebView() // 웹뷰 설정

    } //onCreate

    // 메뉴 설정
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return true
    }

    // 메뉴 아이템 클릭
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.android_to_js_menu -> {
                showDialogWithEditText()
                true
            }
            R.id.site_change_menu -> {
                showDialogWithSelection()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Method to show an alert dialog with single choice list items
    private fun showDialogWithSelection(){
        // Late initialize an alert dialog object
        lateinit var dialog:AlertDialog

        // Initialize an array of colors
        val array = arrayOf("RED","GREEN","YELLOW","BLACK","MAGENTA","PINK")

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Choose a color.")

        /*
            **** reference source developer.android.com ***

            AlertDialog.Builder setSingleChoiceItems (CharSequence[] items,
                            int checkedItem,
                            DialogInterface.OnClickListener listener)

                Set a list of items to be displayed in the dialog as the content, you will be
                notified of the selected item via the supplied listener. The list will have a
                check mark displayed to the right of the text for the checked item. Clicking
                on an item in the list will not dismiss the dialog. Clicking on
                a button will dismiss the dialog.

            Parameters
                items CharSequence : the items to be displayed.
                checkedItem int : specifies which item is checked. If -1 no items are checked.

                listener DialogInterface.OnClickListener : notified when an item on the list is
                    clicked. The dialog will not be dismissed when an item is clicked. It will
                    only be dismissed if clicked on a button, if no buttons are supplied
                    it's up to the user to dismiss the dialog.

            Returns
                AlertDialog.Builder : This Builder object to allow for chaining of calls to set methods
        */

        // Set the single choice items for alert dialog with initial selection
        builder.setSingleChoiceItems(array,-1,{_,which->
            // Get the dialog selected item
            val color = array[which]

            // Try to parse user selected color string
            try {
                // Change the layout background color using user selection
//                root_layout.setBackgroundColor(Color.parseColor(color))
//                toast("$color color selected.")
            }catch (e:IllegalArgumentException){
                // Catch the color string parse exception
//                toast("$color color not supported.")
            }

            // Dismiss the dialog
            dialog.dismiss()
        })


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    private fun nativeToJsEventCall(messageToSend: String) {
        Log.d(TAG, "nativeToJsEventCall: messageToSend: $messageToSend")
        myWebView.evaluateJavascript("javascript:nativeToJsEventCall('$messageToSend');", null)
//        myWebView.loadUrl("javascript:nativeToJsEventCall(hdhohsohsohso);")
    }

    private fun showDialogWithEditText(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("JS 로 보낼 내용")

// Set up the input
        val input = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.hint = "보낼 글자를 입력하세요"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("보내기", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            val m_Text = input.text.toString()
            Log.d(TAG, "showDialogWithEditText: m_Text : $m_Text")
            this.nativeToJsEventCall(m_Text)
        })
        builder.setNegativeButton("닫기", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView(){
        this.myWebView = findViewById(R.id.my_webview)

        val webViewBtn: Button = findViewById(R.id.start_webview_btn)

        myWebView.loadUrl(Constants.DEV_JEONG_DAE_RI_URL) // 해당 url 을 로드 한다

        val settings = myWebView.settings
        settings.javaScriptEnabled = true // 자바스크립트 허용 설정
        settings.domStorageEnabled = true // 돔 스토리지 사용 허용

        myWebView.webViewClient = WebViewClient() // 웹뷰 클라이언트 설정을 해야 웹뷰 내에서 보여짐
//        myWebView.webChromeClient = WebChromeClient() // 웹 크롬 클라이언트

        // 웹뷰 크롬 클라이언트 설정

        val myWebClient = MyWebClient(this, this)
//        myWebClient.webViewInterface = this
        myWebView.webChromeClient = myWebClient

        val myWebAppInterface = MyWebAppInterface(this, this)
//        myWebAppInterface.webViewInterface = this
        // 자바스크립트 인터페이스 설정
        myWebView.addJavascriptInterface(myWebAppInterface, "Android")

//        myWebView.setWebContentsDebuggingEnabled(true)

        // 웹뷰 크롬 클라이언트 설정
//        myWebView.webChromeClient = object : WebChromeClient() {
//            // js alert 이벤트를 받는다
//            override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
//                Log.d(TAG, "onJsAlert: $message")
//                Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
//                result.cancel()
//                return true
//            }
//        }
    }

    fun onStartWebviewBtnClicked(view: View){
        Log.d(TAG, "onStartWebviewBtnClicked: ")
        val intent = Intent(this, WebviewActivity::class.java)
        startActivity(intent)
    }

    // 웹뷰 관련
    override fun jsAlertAction(response: WEB_ALERT_RESPONSE) {
        Log.d(TAG, "메인 jsAlertAction: response / $response")
    }

    override fun jsToNativeAlertAction(response: WEB_ALERT_RESPONSE) {
        Log.d(TAG, "메인 jsToNativeAlertAction: response / $response")
    }


} // MainActivity
