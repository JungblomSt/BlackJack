package com.example.blackjack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _textVisible = MutableLiveData<Boolean>(true)
    val textVisible: LiveData<Boolean> get() = _textVisible

    fun setTextVisible(isVisible: Boolean){
        _textVisible.value = isVisible
    }
}