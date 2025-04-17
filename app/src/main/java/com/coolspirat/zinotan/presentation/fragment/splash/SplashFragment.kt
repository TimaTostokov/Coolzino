package com.coolspirat.zinotan.presentation.fragment.splash

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.data.prefs.LinkPrefs
import com.coolspirat.zinotan.databinding.FragmentSplashBinding
import com.coolspirat.zinotan.utils.Config
import com.coolspirat.zinotan.utils.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val prefs by lazy { LinkPrefs(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideSystemBars()

        viewLifecycleOwner.lifecycleScope.launch {
            delay(1_500)

            val link = getOrFetchInnerLink()
            val useWeb = URLUtil.isHttpUrl(link) || URLUtil.isHttpsUrl(link)

            if (useWeb) {
                if (!prefs.wasSeen()) prefs.markSeen()
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashToWebView()
                )
            } else {
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashToFirstFragment()
                )
            }
        }
    }

    private suspend fun getOrFetchInnerLink(): String {
        val saved = prefs.saved()
        if (saved != null && (saved.startsWith("http://") || saved.startsWith("https://"))) {
            return saved
        }
        val fetched = fetchInnerLink()
        if (fetched.startsWith("http://") || fetched.startsWith("https://")) {
            prefs.save(fetched)
        }
        return fetched
    }

    private suspend fun fetchInnerLink(): String = withContext(Dispatchers.IO) {
        runCatching {
            URL(Config.OUTER_URL).openStream().bufferedReader().useLines { lines ->
                lines.firstOrNull { it.trim().startsWith("http://") || it.trim().startsWith("https://") }
                    ?.trim() ?: ""
            }
        }.getOrDefault("")
    }

    private fun hideSystemBars() {
        requireActivity().window.apply {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
        }
    }
}
