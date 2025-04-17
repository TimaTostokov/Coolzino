package com.coolspirat.zinotan.presentation.fragment.splash

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.data.prefs.LinkPrefs
import com.coolspirat.zinotan.databinding.FragmentSplashBinding
import com.coolspirat.zinotan.utils.Config
import com.coolspirat.zinotan.utils.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val prefs by lazy { LinkPrefs(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideSystemBars()

        viewLifecycleOwner.lifecycleScope.launch {
            delay(1_500)

            val stored = prefs.saved()
            val link = stored ?: Config.EMBEDDED_URL.also { prefs.save(it) }

            if (link.startsWith("http")) {
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

    private fun hideSystemBars() {
        requireActivity().window.apply {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
    }
}

