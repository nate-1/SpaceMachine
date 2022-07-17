package com.nategus.spacemachine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.nategus.spacemachine.GameViewModel
import com.nategus.spacemachine.R
import com.nategus.spacemachine.databinding.FragmentGameBinding
import com.nategus.spacemachine.fragment.SwitchesFragment

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private var _binding: FragmentGameBinding? = null

    private lateinit var supportFragmentManager: FragmentManager
    private var _supportFragmentManager: FragmentManager? = null

    private val gameViewModel: GameViewModel by activityViewModels()

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

        var listRandNb: List<Int> = emptyList()
        for (i in 1..4) {

            val fragmentContainerId = when (i) {
                1 -> R.id.item_slot1
                2 -> R.id.item_slot2
                3 -> R.id.item_slot3
                4 -> R.id.item_slot4
                else -> 0
            }

            var randval: Int
            do {
                randval = (1..4).random()
            } while (listRandNb.contains(randval))
            listRandNb += randval

            val fragment = when (randval) {
                1 -> ButtonFragment()
                2 -> RadioButtonFragment()
                3 -> SliderFragment()
                4 -> SwitchesFragment()
                else -> ButtonFragment()
            }

            println("hey " + fragmentContainerId + " " + fragment::class )

            transaction.replace(fragmentContainerId, fragment)
        }

        transaction.commit()

    }

    companion object {
    }

}