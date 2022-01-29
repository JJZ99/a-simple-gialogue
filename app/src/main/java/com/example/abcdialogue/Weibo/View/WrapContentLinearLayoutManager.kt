package com.example.abcdialogue.Weibo.View

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class WrapContentLinearLayoutManager : LinearLayoutManager {
    val TAG = "WrapContentLinearLayoutManager"
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout) {}

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int, ) : super(context, attrs, defStyleAttr, defStyleRes) {}

//    override fun supportsPredictiveItemAnimations(): Boolean {
//        return false
//    }

    /**
     * @see https://stackoverflow.com/questions/31759171/recyclerview-and-java-lang-indexoutofboundsexception-inconsistency-detected-in
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        }catch (e:Exception){
            Log.e(TAG, e.toString())
        }
    }
}