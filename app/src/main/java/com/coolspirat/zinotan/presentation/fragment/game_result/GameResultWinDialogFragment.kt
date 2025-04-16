package com.coolspirat.zinotan.presentation.fragment.game_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.databinding.BottomsheetWinBinding

class GameResultWinDialogFragment : DialogFragment() {

    private var _binding: BottomsheetWinBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = GameResultWinDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomsheetWinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToMenu.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.action_global_fourFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialogTheme
    }

}