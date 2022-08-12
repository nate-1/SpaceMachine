package com.nategus.spacemachine.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.*
import com.nategus.spacemachine.Element
import com.nategus.spacemachine.databinding.FragmentGameBinding
import kotlin.random.Random

val titles: List<String> = listOf("cymitharra", "tonsus", "delphinus", "tursio", "Athenae", "Patrae", "aperio", "declinatio", "canis", "flos", "Roma", "Lutetia", "Londinium", "noceo", "nocere", "quisquiliae", "Neapolis", "Antium", "Corinthus", "bellum", "eicio", "gnome", "haruspex", "icon", "moveo", "aniba", "oleum", "speculum", "utinam", "xiphias", "zelus", "porcus_marinus", "Graecia", "operio", "autem", "bos", "helium", "aut", "androgynus", "bovinus", "draco", "equus", "bellicus", "zodiacus", "eques", "formica", "gallina", "porcus", "guerra", "lycophthalmus", "annihilo", "mare", "adalligo", "Aegyptus", "Italia", "Graecus", "tonsor", "oeconomia", "caulis", "homonymus", "illino", "decoctum", "folium", "amicus", "O", "decoquo", "illitus", "sucus", "efficax", "caninus", "sativus", "epiphora", "cauliculus", "tormina", "ulcus", "fimum", "sarcasmus", "caprinus", "vomitio", "inclino", "duo", "tres", "quattuor", "quinque", "primus", "tripus", "septem", "ligatura", "anthropophagus", "octo", "octavus", "unio", "novem", "decem", "chiliometrum", "epigramma", "centum", "mille", "quintus", "nox", "quartus", "tertius", "macropus", "septimus", "nonus", "hippotoxotae", "synonymum", "Iaponia", "phthir", "diphthongus", "ab", "abdo", "abeo", "abhinc", "abicio", "abigo", "aboleo", "abscedo", "sol", "absens", "absisto", "absolvo", "abstinens", "abstineo", "abstuli", "prior", "novies", "smaragdus", "absum", "absurdus", "prae", "abundo", "Tocio", "frater", "eo", "accedo", "res", "secundus", "accendo", "sextus", "quincuplex", "accido", "simplex", "Hoccaido", "Ocinava", "beryllium", "accipio", "Zoroastreus", "Zopyriatim", "lithium", "accommodo", "caerimonia", "dies_Solis", "dies_Lunae", "dies_Martis", "uranium", "Mars", "nex", "accumbo", "Kalendae", "hydrogenium", "Iuppiter", "nux", "accuso", "palladium", "pirata", "acer", "Venus", "terbium", "Saturnus", "homunculus", "sum", "actinium", "Pluto", "daemon", "acerbus", "borum", "volcanus", "diabolus", "magnesium", "Mercurius", "gladius", "acervus", "Achaicus", "ununseptium", "Iuno", "elementum", "cupido", "ignis", "vanitas", "manatus", "neon", "achates", "Demeter", "lacrimo", "Acheron", "argon", "luna", "Minerva", "Achilles", "ununoctium", "lupus", "radon", "ununbium", "ununhexium", "ununpentium", "ununquadium", "acies", "nix", "cornix", "Kyotum", "leo", "Argentina", "Nara", "Ciusium", "unununium", "ununtrium", "tellurium", "barium", "platinum", "berkelium", "Nagasacium", "Fusius", "nomas", "Vindobona", "nobelium", "nomen", "Meacum", "Osaca", "Hirosima", "acriter", "cadmium", "orca", "Varsovia", "Hieus", "Lotharingia", "neptunium")
val instructionSentences: List<String> = listOf("I think you should", "You gotta", "You better", "You probably should", "You need to")
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private var _binding: FragmentGameBinding? = null

    private lateinit var supportFragmentManager: FragmentManager
    private var _supportFragmentManager: FragmentManager? = null

    private var usedTitles: List<String> = emptyList()

    private val buttons: MutableList<Element> = emptyList<Element>().toMutableList()
    private val switches: MutableList<Element> = emptyList<Element>().toMutableList()

    private var currentElementId: Int = 0
    private var life: Int = 100
    private var listItemSize: Int = 5
    private var wave: Int = 1

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

        binding.replayButton.setOnClickListener {
            it.visibility = View.INVISIBLE

            binding.progressbar.progress = 100
            binding.scoreLabel.text = "1"

            life = 100
            listItemSize = 5
            wave = 1
            setUpGameBoard()
            generateAction()
        }
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generateAction()
    }

    fun setUpGameBoard() {

        for(i in (1..listItemSize)) {
            switches += Element(i, getTitle(), (0..1).random() == 1)
            buttons += Element(i+listItemSize, getTitle(), false)
        }

        binding.switchRecyclerView.adapter = SwitchAdaptor(switches) { position, isChecked ->
            val el = switches[position]
            el.value = isChecked

            checkAction(el)
        }

        binding.buttonRecyclerView.adapter = ButtonAdaptor(buttons) {
            val el = buttons[it]

            checkAction(el)
        }

        // https://stackoverflow.com/questions/30007956/how-to-center-items-of-a-recyclerview
        binding.buttonRecyclerView.layoutManager = FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.COLUMN
            flexWrap = FlexWrap.NOWRAP
        }
    }

    fun generateAction() {
        val typeRand = (1..2).random()
        val listToGetFrom: List<Element> = when(typeRand) {
            1 -> switches
            2 -> buttons
            else -> return
        }

        val element = listToGetFrom.random()

        println(element)

        var instruction = ""
        if(typeRand == 1) {
            val action: String = if(element.value) "off" else "on"
            instruction = "${instructionSentences.random()} turn $action the ${element.name}"
        }
        else if(typeRand == 2) {
            instruction = "${instructionSentences.random()} press the ${element.name}"
        }

        println(instruction)

        currentElementId = element.id
        binding.instructionLabel.text = instruction
    }

    fun checkAction(el: Element) {
        if(el.id == currentElementId) {
            generateAction()
            wave++

            binding.scoreLabel.text = wave.toString()
        }
        else {
            life -= 10
            binding.progressbar.progress = life

            if(life <= 0) {
                clear()
                binding.replayButton.visibility = View.VISIBLE
                binding.instructionLabel.text = "Ya dead"
            }
        }
    }

    fun getTitle(): String {
        var title: String

        do {
            title = titles.random()
        }
        while(usedTitles.contains(title))

        usedTitles += title
        return title
    }

    fun clear(): Unit {
        binding.switchRecyclerView.adapter?.notifyItemRangeRemoved(0, listItemSize)
        binding.buttonRecyclerView.adapter?.notifyItemRangeRemoved(0, listItemSize)

        switches.clear()
        buttons.clear()
    }

    companion object {
    }

}