package com.connected.theapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@AllOpen
class MainViewModel : ViewModel() {

    val searchResult: MutableLiveData<List<User>> = MutableLiveData()

    fun search(text: String) {

    }


}