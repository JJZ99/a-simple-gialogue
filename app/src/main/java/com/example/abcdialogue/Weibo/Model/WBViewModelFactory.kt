package com.example.abcdialogue.Weibo.Model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WBViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WBViewModel::class.java)) {
            return WBViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}