package com.coolspirat.zinotan.presentation.fragment.splash

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.data.prefs.LinkPrefs
import com.coolspirat.zinotan.databinding.FragmentSplashBinding
import com.coolspirat.zinotan.utils.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    private val configUrl = "https://raw.githubusercontent.com/temircode/test-link/main/link.txt"

    private val prefs by lazy { LinkPrefs(requireContext()) }

    private val handler = Handler(Looper.getMainLooper())
    private val fallbackRunnable = Runnable {
        findNavController().navigate(R.id.action_splashFragment_to_firstFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().window.apply {
            statusBarColor = android.graphics.Color.TRANSPARENT
            navigationBarColor = android.graphics.Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }

        handler.postDelayed(fallbackRunnable, 2000)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            if (prefs.saved() == null) {
                val link = fetchLink().trim()
                prefs.save(link)
            }
            handler.removeCallbacks(fallbackRunnable)
            route()
        }
    }

    private suspend fun fetchLink(): String = withContext(Dispatchers.IO) {
        runCatching { URL(configUrl).readText() }.getOrDefault("")
    }

    private fun route() {
        val link = prefs.saved().orEmpty()

        if (link.startsWith("http://") || link.startsWith("https://")) {
            if (!prefs.wasSeen()) prefs.markSeen()
            findNavController().navigate(R.id.action_splash_to_webView)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_firstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(fallbackRunnable)
        activity?.window?.let { window ->
            window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.blur)
        }
    }

}