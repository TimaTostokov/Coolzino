package com.coolspirat.zinotan.presentation.fragment.gameplay

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.data.prefs.PrefsManager
import com.coolspirat.zinotan.databinding.FragmentGamePlayBinding
import com.coolspirat.zinotan.presentation.fragment.game_result.GameResultWinDialogFragment
import kotlin.random.Random

class GamePlayFragment : Fragment() {

    companion object {
        private const val ARG_LEVEL_NUMBER = "ARG_LEVEL_NUMBER"
        fun newInstance(levelNumber: Int): GamePlayFragment {
            val fragment = GamePlayFragment()
            val args = Bundle()
            args.putInt(ARG_LEVEL_NUMBER, levelNumber)
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentGamePlayBinding? = null
    private val binding get() = _binding!!

    private var levelNumber: Int = 0
    private lateinit var prefsManager: PrefsManager

    private var lives = 3
    private var score = 0
    private var objectsToDrop = 0
    private var isGameRunning = false
    private var gameOverShown = false

    private val fallingDrawables = listOf(
        R.drawable.img_dm1,
        R.drawable.img_dm2,
        R.drawable.img_dm3,
        R.drawable.img_dm4,
        R.drawable.img_dm5
    )

    private var pirateGender: String? = null

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamePlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        levelNumber = arguments?.getInt(ARG_LEVEL_NUMBER) ?: 1
        pirateGender = arguments?.getString("pirateGender")

        prefsManager = PrefsManager(requireContext())

        binding.tvHealth.text = lives.toString()
        binding.tvScore.text = score.toString()

        val pirotImageView: ImageView = binding.pirot
        when (pirateGender) {
            "male" -> pirotImageView.setImageResource(R.drawable.img_crivoy_pirat)
            "female" -> pirotImageView.setImageResource(R.drawable.img_crivoy_ne_pirat)
            else -> pirotImageView.setImageResource(R.drawable.img_crivoy_pirat)
        }

        binding.startPlay.setOnClickListener {
            startGame()
        }
    }

    private fun startGame() {
        if (isGameRunning) return
        isGameRunning = true
        binding.startPlay.visibility = View.GONE

        lives = 3
        score = 0
        binding.tvHealth.text = lives.toString()
        binding.tvScore.text = score.toString()

        val baseCount = Random.nextInt(10, 16)
        objectsToDrop = baseCount + (levelNumber - 1)

        val baseFallDuration = (3000 - 100 * (levelNumber - 1)).coerceAtLeast(1500)
        val dropInterval = 1000L
        var droppedCount = 0

        handler.postDelayed(object : Runnable {
            override fun run() {
                if (droppedCount < objectsToDrop && lives > 0) {
                    activity?.runOnUiThread {
                        dropOneObject(baseFallDuration)
                    }
                    droppedCount++
                    handler.postDelayed(this, dropInterval)
                }
            }
        }, dropInterval)
    }

    private fun dropOneObject(fallDuration: Int) {
        if (!isGameRunning) return
        val binding = _binding ?: return

        val fallingItem = ImageView(requireContext()).apply {
            setImageResource(fallingDrawables.random())
            adjustViewBounds = true
            elevation = 4 * resources.displayMetrics.density
            val sizePx = (50 * resources.displayMetrics.density).toInt()
            layoutParams = ViewGroup.LayoutParams(sizePx, sizePx)
            tag = "falling"
        }

        val rootLayout = binding.rootLayout
        rootLayout.addView(fallingItem)

        val sizePx = fallingItem.layoutParams.width
        val screenWidth = rootLayout.width
        val maxX = screenWidth - sizePx
        val randomX = Random.nextInt(0, maxX.coerceAtLeast(0))

        fallingItem.translationX = randomX.toFloat()
        fallingItem.translationY = -sizePx.toFloat()

        fallingItem.setOnClickListener {
            rootLayout.removeView(fallingItem)
            score++
            binding.tvScore.text = score.toString()
        }

        val animator =
            ValueAnimator.ofFloat(-sizePx.toFloat(), rootLayout.height + sizePx.toFloat())
        animator.duration = fallDuration.toLong()
        animator.addUpdateListener { valueAnimator ->
            fallingItem.translationY = valueAnimator.animatedValue as Float
        }
        animator.doOnEnd {
            if (rootLayout.indexOfChild(fallingItem) != -1) {
                rootLayout.removeView(fallingItem)
                lives--
                binding.tvHealth.text = lives.toString()
                if (lives <= 0) {
                    restartLevel()
                } else {
                    checkIfGameOver()
                }
            } else {
                checkIfGameOver()
            }
        }
        animator.start()
    }

    private fun checkIfGameOver() {
        val totalUsed = score + (3 - lives)
        if (totalUsed == objectsToDrop && lives > 0) {
            showWinResult()
        }
    }

    private fun showWinResult() {
        if (gameOverShown) return
        gameOverShown = true
        isGameRunning = false

        val currentHighest =
            prefsManager.getHighestLevelCompleted(pirateGender)
        if (levelNumber > currentHighest) {
            prefsManager.setHighestLevelCompleted(pirateGender, levelNumber)
        }
        GameResultWinDialogFragment.newInstance().show(parentFragmentManager, "winDialog")
    }

    private fun restartLevel() {
        isGameRunning = false

        val binding = _binding ?: return
        handler.removeCallbacksAndMessages(null)

        val toRemove = mutableListOf<View>()
        for (i in 0 until binding.rootLayout.childCount) {
            val child = binding.rootLayout.getChildAt(i)
            if (child.tag == "falling") {
                toRemove.add(child)
            }
        }
        toRemove.forEach { binding.rootLayout.removeView(it) }

        binding.startPlay.visibility = View.VISIBLE
        lives = 3
        score = 0
        binding.tvHealth.text = lives.toString()
        binding.tvScore.text = score.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacksAndMessages(null)
    }

}