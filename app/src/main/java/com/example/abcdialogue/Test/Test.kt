package com.example.abcdialogue.Test

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class Test

fun main() {
    Observable.create(object : ObservableOnSubscribe<Int> {
        override fun subscribe(emitter: ObservableEmitter<Int>) {
            //事件产生的地方，比如保存文件、请求网络等
            if (10 % 2 == 1) {
                emitter.onNext(1)
            } else {
                emitter.onNext(0)
                //error和complete是互斥的就算你这里都写了，但是只会回调用一个，前面的那个
//                emitter.onError(NullPointerException("空指针了，给老子爬"))
                //emitter.onComplete()
            }
            Log.i("RXJAVA" ,"Emitter.onNext:"+Thread.currentThread().name.toString())

        }
    }).observeOn(Schedulers.io()).

    map {   //做一次转换，多余操作，就是想用一下
        Log.i("RXJAVA","map"+Thread.currentThread().name.toString())
        it.toString()
    }
        .observeOn(Schedulers.computation())
        .subscribeOn(Schedulers.computation())//设置事件发生的线程

        .subscribe(object : Observer<String> {

            //这个方法在onNext之前被调用
            override fun onSubscribe(d: Disposable) {
            }
            override fun onNext(t: String) {
                Log.i("RXJAVA" , "Observer.onNext:"+Thread.currentThread().name.toString())

            }
            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            //这个方法在onNext之后被调用
            override fun onComplete() {

            }
        })

}