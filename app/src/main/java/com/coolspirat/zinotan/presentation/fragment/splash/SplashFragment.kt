package com.coolspirat.zinotan.presentation.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.databinding.FragmentSplashBinding
import com.coolspirat.zinotan.utils.viewBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    private val handler = Handler(Looper.getMainLooper())
    private val navigateRunnable = Runnable {
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

        handler.postDelayed(navigateRunnable, 1400)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(navigateRunnable)

        activity?.window?.let { window ->
            window.navigationBarColor = ContextCompat.getColor(requireContext(), R.color.blur)
        }
    }

}