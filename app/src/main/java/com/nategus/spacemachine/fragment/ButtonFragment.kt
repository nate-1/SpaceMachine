package com.nategus.spacemachine.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nategus.spacemachine.R
import com.nategus.spacemachine.databinding.FragmentButtonBinding
import com.nategus.spacemachine.databinding.FragmentSwitchesBinding

class ButtonFragment : Fragment() {

    private lateinit var binding: FragmentButtonBinding
    private var _binding: FragmentButtonBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentButtonBinding.inflate(inflater, container, false)
        binding = _binding!!

        return binding.root
    }

    companion object {
    }
}