package com.coolspirat.zinotan.presentation.fragment.three

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coolspirat.zinotan.R
import com.coolspirat.zinotan.databinding.FragmentThreeBinding

class ThreeFragment : Fragment() {

    private var _binding: FragmentThreeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnLeft.setOnClickListener {
            val bundle = Bundle().apply {
                putString("pirateGender", "male")
            }
            findNavController().navigate(R.id.action_threeFragment_to_fourFragment, bundle)
        }
        binding.btnRight.setOnClickListener {
            val bundle = Bundle().apply {
                putString("pirateGender", "female")
            }
            findNavController().navigate(R.id.action_threeFragment_to_fourFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
