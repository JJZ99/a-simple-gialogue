package com.example.abcdialogue.Weibo.Prestener

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class IPresenter(owner: LifecycleOwner) : DefaultLifecycleObserver {
    private val TAG = "MyDefaultLifecycleObserver"

     override fun onCreate(owner: LifecycleOwner) {
         super.onCreate(owner)
         Log.i(TAG, "onCreate,${owner.lifecycle.currentState}")
     }

     override fun onDestroy(owner: LifecycleOwner) {
         super.onDestroy(owner)
         Log.i(TAG, "onDestroy,${owner.lifecycle.currentState}")
     }

     override fun onPause(owner: LifecycleOwner) {
         super.onPause(owner)
         Log.i(TAG, "onPause,${owner.lifecycle.currentState}")
     }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.i(TAG, "onResume,${owner.lifecycle.currentState}")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.i(TAG, "onStart,${owner.lifecycle.currentState}")

    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.i(TAG, "onStop,${owner.lifecycle.currentState}")
    }

}