package com.nategus.spacemachine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.nategus.spacemachine.R
import com.nategus.spacemachine.databinding.FragmentGameBinding
import com.nategus.spacemachine.fragment.GameItem1Fragment

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private var _binding: FragmentGameBinding? = null

    private lateinit var supportFragmentManager: FragmentManager
    private var _supportFragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        binding = _binding!!

        _supportFragmentManager = activity?.supportFragmentManager
        supportFragmentManager = _supportFragmentManager!!

        setUpGameBoard()

        return binding.root
    }

    fun setUpGameBoard() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.item_slot1, GameItem1Fragment())
        transaction.replace(R.id.item_slot2, GameItem1Fragment())

        transaction.commit()
    }

    companion object {
    }

}