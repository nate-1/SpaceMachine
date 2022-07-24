package com.nategus.spacemachine.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.google.android.flexbox.*
import com.nategus.spacemachine.Element
import com.nategus.spacemachine.databinding.FragmentGameBinding

val titles: List<String> = listOf("cymitharra", "tonsus", "delphinus", "tursio", "Athenae", "Patrae", "aperio", "declinatio", "canis", "flos", "Roma", "Lutetia", "Londinium", "noceo", "nocere", "quisquiliae", "Neapolis", "Antium", "Corinthus", "bellum", "eicio", "gnome", "haruspex", "icon", "moveo", "aniba", "oleum", "speculum", "utinam", "xiphias", "zelus", "porcus_marinus", "Graecia", "operio", "autem", "bos", "helium", "aut", "androgynus", "bovinus", "draco", "equus", "bellicus", "zodiacus", "eques", "formica", "gallina", "porcus", "guerra", "lycophthalmus", "annihilo", "mare", "adalligo", "Aegyptus", "Italia", "Graecus", "tonsor", "oeconomia", "caulis", "homonymus", "illino", "decoctum", "folium", "amicus", "O", "decoquo", "illitus", "sucus", "efficax", "caninus", "sativus", "epiphora", "cauliculus", "tormina", "ulcus", "fimum", "sarcasmus", "caprinus", "vomitio", "inclino", "duo", "tres", "quattuor", "quinque", "primus", "tripus", "septem", "ligatura", "anthropophagus", "octo", "octavus", "unio", "novem", "decem", "chiliometrum", "epigramma", "centum", "mille", "quintus", "nox", "quartus", "tertius", "macropus", "septimus", "homo", "nonus", "hippotoxotae", "synonymum", "Iaponia", "phthir", "diphthongus", "ab", "abdo", "abeo", "abhinc", "abicio", "abigo", "aboleo", "abscedo", "sol", "absens", "absisto", "absolvo", "abstinens", "abstineo", "abstuli", "prior", "novies", "smaragdus", "absum", "absurdus", "prae", "abundo", "Tocio", "frater", "eo", "accedo", "res", "secundus", "accendo", "sextus", "quincuplex", "accido", "simplex", "Hoccaido", "Ocinava", "beryllium", "accipio", "Zoroastreus", "Zopyriatim", "lithium", "accommodo", "caerimonia", "dies_Solis", "dies_Lunae", "dies_Martis", "uranium", "Mars", "nex", "accumbo", "Kalendae", "hydrogenium", "Iuppiter", "nux", "accuso", "palladium", "pirata", "acer", "Venus", "terbium", "Saturnus", "homunculus", "sum", "actinium", "Pluto", "daemon", "acerbus", "borum", "volcanus", "diabolus", "magnesium", "Mercurius", "gladius", "acervus", "Achaicus", "ununseptium", "Iuno", "elementum", "cupido", "ignis", "vanitas", "manatus", "neon", "achates", "Demeter", "lacrimo", "Acheron", "argon", "luna", "Minerva", "Achilles", "ununoctium", "lupus", "radon", "ununbium", "ununhexium", "ununpentium", "ununquadium", "acies", "nix", "cornix", "Kyotum", "leo", "Argentina", "Nara", "Ciusium", "unununium", "ununtrium", "tellurium", "barium", "platinum", "berkelium", "Nagasacium", "Fusius", "nomas", "Vindobona", "nobelium", "nomen", "Meacum", "Osaca", "Hirosima", "acriter", "cadmium", "orca", "Varsovia", "Hieus", "Lotharingia", "neptunium")
val instructionSentences: List<String> = listOf("I think you should", "You gotta", "You better", "You probably should")
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private var _binding: FragmentGameBinding? = null

    private lateinit var supportFragmentManager: FragmentManager
    private var _supportFragmentManager: FragmentManager? = null

    private val gameViewModel: GameViewModel by activityViewModels()

    private var usedTitles: List<String> = emptyList()

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

        val switches: MutableList<Element> = emptyList<Element>().toMutableList()
        val buttons: MutableList<Element> = emptyList<Element>().toMutableList()

        for(i in (1..4)) {
            switches += Element(0, getTitle(),false)
            buttons += Element(0, getTitle(), false)
        }

        println(switches)

        gameViewModel.switches.postValue(switches)
        gameViewModel.buttons.postValue(buttons)

        binding.switchRecyclerView.adapter = SwitchAdaptor(switches) { position, isChecked ->
            switches[position].value = isChecked
        }

        binding.buttonRecyclerView.adapter = ButtonAdaptor(buttons) {

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
        val listToGetFrom: List<Element>? = when(typeRand) {
            1 -> gameViewModel.switches.value
            2 -> gameViewModel.buttons.value

            else -> gameViewModel.buttons.value
        }

        if(listToGetFrom == null) {
                return
        }

        val element = listToGetFrom.random()

        var instruction = ""
        if(typeRand == 1) {
            val action: String = if(element.value) "off" else "on"
            instruction = "${instructionSentences.random()} turn $action the ${element.name}"
        }
        else if(typeRand == 2) {
            instruction = "${instructionSentences.random()} press the ${element.name}"
        }

        binding.instructionLabel.text = instruction
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

    companion object {
    }

}