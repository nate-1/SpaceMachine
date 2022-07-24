package com.nategus.spacemachine.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.nategus.spacemachine.*

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val wave = MutableLiveData<Int>(0) // number of wave in then round
    val round = MutableLiveData<Int>(0)

    val buttons = MutableLiveData<List<Element>>(null)
    val switches = MutableLiveData<List<Element>>(null)

    val numberOfError = MutableLiveData<Int>(0)
}