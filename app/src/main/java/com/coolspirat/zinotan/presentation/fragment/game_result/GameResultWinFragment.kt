package com.coolspirat.zinotan.presentation.fragment.game_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.databinding.BottomsheetWinBinding

class GameResultWinFragment : Fragment() {

    private val binding by lazy {
        BottomsheetWinBinding.inflate(layoutInflater)
    }

    companion object {
        fun newInstance(): GameResultWinFragment {
            return GameResultWinFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_global_fourFragment)
        }
    }
}

