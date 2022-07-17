package com.nategus.spacemachine.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nategus.spacemachine.R
import com.nategus.spacemachine.databinding.FragmentRadioButtonBinding
import com.nategus.spacemachine.databinding.FragmentSwitchesBinding

class RadioButtonFragment : Fragment() {

    private lateinit var binding: FragmentRadioButtonBinding
    private var _binding: FragmentRadioButtonBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRadioButtonBinding.inflate(inflater, container, false)
        binding = _binding!!

        return binding.root
    }

    companion object {

    }
}