package com.sagycorp.myutd.utils

import android.app.Activity
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class CustomWebView(private val activity: Activity) : WebViewClient() {

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        view?.loadUrl("about:blank")
        Toast.makeText(activity, "No internet", Toast.LENGTH_SHORT).show()

    }

}
