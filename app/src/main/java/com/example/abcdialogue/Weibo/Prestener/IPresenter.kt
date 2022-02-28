package com.example.abcdialogue.Weibo.Prestener

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class IPresenter(owner: LifecycleOwner, val name: String) : DefaultLifecycleObserver {
    private val TAG = "MyDefaultLifecycleObserver"

     override fun onCreate(owner: LifecycleOwner) {
         super.onCreate(owner)
         Log.e(TAG, "onCreate,${name}")
     }

     override fun onDestroy(owner: LifecycleOwner) {
         super.onDestroy(owner)
         Log.e(TAG, "onDestroy,${name}")
     }

     override fun onPause(owner: LifecycleOwner) {
         super.onPause(owner)
         Log.e(TAG, "onPause,${name}")
     }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.e(TAG, "onResume,${name}")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.e(TAG, "onStart,${name}")

    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.e(TAG, "onStop,${name}")
    }

}