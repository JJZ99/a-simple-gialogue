package com.example.abcdialogue.Weibo.Util.Net

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.gyf.immersionbar.ImmersionBar
import com.hitomi.tilibrary.transfer.TransferConfig
import com.hitomi.tilibrary.transfer.Transferee
import com.hitomi.tilibrary.transfer.Transferee.OnTransfereeStateChangeListener

class MyTransferee private constructor(var context: Context) {
    val TAG = "MyTransferee"

    private val transferee: Transferee by lazy {
        Transferee.getDefault(context)
    }
    val immersionBar : ImmersionBar by lazy {
        ImmersionBar.with(context as FragmentActivity)
    }

    fun apply(config: TransferConfig): MyTransferee {
        Log.i(TAG,"apply")
        transferee.apply(config)
        return this
    }
    fun show(){
        Log.i(TAG,"show")
        transferee.show()
        immersionBar.transparentNavigationBar().init()
    }
    fun dismiss() {
        Log.i(TAG,"dismiss")
        transferee.dismiss()
        immersionBar.reset().init()
    }


    fun clear() {
        Log.i(TAG,"clear")

        transferee.clear()
    }

    fun destroy() {
        Log.i(TAG,"destroy")
        transferee.destroy()
    }
    fun setOnTransfereeStateChangeListener(listener: OnTransfereeStateChangeListener) {
        Log.i(TAG,"setOnTransfereeStateChangeListener")
        transferee.setOnTransfereeStateChangeListener(listener)
    }


    companion object {
        fun getDefault(context: Context): MyTransferee = MyTransferee(context)
    }

}