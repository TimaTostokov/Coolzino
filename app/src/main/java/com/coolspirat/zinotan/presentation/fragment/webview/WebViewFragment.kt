package com.coolspirat.zinotan.presentation.fragment.webview

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.data.prefs.LinkPrefs
import com.coolspirat.zinotan.databinding.FragmentWebViewBinding
import com.coolspirat.zinotan.utils.viewBinding

class WebViewFragment : Fragment(R.layout.fragment_web_view) {

    private val binding by viewBinding(FragmentWebViewBinding::bind)
    private val prefs by lazy { LinkPrefs(requireContext()) }

    private var webView: WebView? = null
    private var webState: Bundle? = null

    override fun onResume() {
        super.onResume()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
    }

    override fun onStop() {
        super.onStop()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                WebViewFragmentDirections.actionWebViewToFirstFragment()
            )
        }
    }

    private fun setupWebView() {
        webView = binding.web

        webView?.apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            CookieManager.getInstance().apply {
                setAcceptCookie(true)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setAcceptThirdPartyCookies(this@WebViewFragment.webView!!, true)
                    flush()
                }
            }

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView, request: WebResourceRequest
                ) = false
            }

            if (webState != null) {
                restoreState(webState!!)
            } else {
                loadUrl(prefs.saved().orEmpty())
            }
        }
    }

    override fun onPause() {
        super.onPause()
        webView?.let { wv ->
            webState = Bundle().also { wv.saveState(it) }
        }
    }

    override fun onDestroyView() {
        webView?.apply {
            stopLoading()
            loadUrl("about:blank")
            clearHistory()
            destroy()
        }
        webView = null
        webState = null
        super.onDestroyView()
    }
}
