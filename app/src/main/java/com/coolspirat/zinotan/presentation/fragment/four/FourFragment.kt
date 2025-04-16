package com.coolspirat.zinotan.presentation.fragment.four

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.data.model.Level
import com.coolspirat.zinotan.data.prefs.PrefsManager
import com.coolspirat.zinotan.databinding.FragmentFourBinding

class FourFragment : Fragment() {

    private var _binding: FragmentFourBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: LevelsAdapter
    private lateinit var prefsManager: PrefsManager

    private val levelImages = listOf(
        R.drawable.img_item_lvl, R.drawable.img_level_2, R.drawable.img_lvl3,
        R.drawable.img_lvl4, R.drawable.img_lvl5, R.drawable.img_lvl6,
        R.drawable.img_lvl7, R.drawable.img_lvl8, R.drawable.img_lvl9
    )

    private var pirateGender: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvLevels.layoutManager = GridLayoutManager(requireContext(), 3)

        pirateGender = arguments?.getString("pirateGender")
        prefsManager = PrefsManager(requireContext())

        setupLevels()

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setupLevels() {
        val highestLevelCompleted =
            prefsManager.getHighestLevelCompleted(pirateGender)

        val levelsList = (1..9).map { levelNumber ->
            Level(
                levelNumber = levelNumber,
                imageResId = levelImages[levelNumber - 1],
                isUnlocked = levelNumber <= (highestLevelCompleted + 1)
            )
        }

        adapter = LevelsAdapter(levelsList) { level ->
            if (level.isUnlocked) {
                val bundle = Bundle().apply {
                    putInt("ARG_LEVEL_NUMBER", level.levelNumber)
                    putString("pirateGender", pirateGender)
                }
                findNavController().navigate(R.id.gamePlayFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Уровень закрыт!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.rvLevels.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}