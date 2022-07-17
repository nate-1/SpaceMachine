package com.nategus.spacemachine

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class GameViewModel(application: Application) : AndroidViewModel(application) {

    val wave = MutableLiveData<Int>(0)

    val element = MutableLiveData<List<Element>>(null)

    val numberOfError = MutableLiveData<Int>(0)
}