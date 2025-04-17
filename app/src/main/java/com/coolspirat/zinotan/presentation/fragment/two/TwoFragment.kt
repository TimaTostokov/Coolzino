package com.coolspirat.zinotan.presentation.fragment.two

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.databinding.FragmentTwoBinding

class TwoFragment : Fragment() {

    private val binding by lazy {
        FragmentTwoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLeft.setOnClickListener {
            findNavController().navigate(R.id.action_twoFragment_to_threeFragment)
        }
        binding.btnRight.setOnClickListener {
            findNavController().navigate(R.id.action_twoFragment_to_threeFragment)
        }
    }

}