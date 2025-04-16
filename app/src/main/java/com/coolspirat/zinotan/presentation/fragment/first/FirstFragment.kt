package com.coolspirat.zinotan.presentation.fragment.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    private val binding by lazy {
        FragmentFirstBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playBtn.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_twoFragment)
        }
    }
}