package com.coolspirat.zinotan.presentation.fragment.webview

import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.data.prefs.LinkPrefs
import com.coolspirat.zinotan.databinding.FragmentWebViewBinding
import com.coolspirat.zinotan.utils.viewBinding

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val binding by viewBinding(FragmentWebViewBinding::bind)
    private val prefs by lazy { LinkPrefs(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CookieManager.getInstance().setAcceptCookie(true)

        binding.web.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        binding.web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView, request: WebResourceRequest
            ) = false
        }

        val urlToLoad = prefs.saved().orEmpty()
        binding.web.loadUrl(urlToLoad)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.web.destroy()
    }

}