package com.example.mynotes

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    val fragmentId = MutableLiveData(1)

    fun changeFragmentID(id: Int): Boolean{
        fragmentId.value = id
        return true
    }
}