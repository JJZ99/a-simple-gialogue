package com.example.abcdialogue.Weibo.Prestener

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class IPresenter2(owner: LifecycleOwner) : LifecycleObserver {
    private val TAG = "MyLifecycleObserver"
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(owner: LifecycleOwner) {
        Log.i(TAG, "onCreate,${owner.lifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        Log.i(TAG, "onDestroy,${owner.lifecycle.currentState}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(owner: LifecycleOwner) {
        Log.i(TAG, "onPause,${owner.lifecycle.currentState}")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(owner: LifecycleOwner) {
        Log.i(TAG, "onResume,${owner.lifecycle.currentState}")
    }

}