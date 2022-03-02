package com.example.abcdialogue.Test

import android.annotation.SuppressLint
import com.example.abcdialogue.Test.TestT.postEvent
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ConcurrentHashMap

object TestT {
    var cachedParams: MutableMap<BaseMetricsEventV2, MutableMap<String, String>> =
        ConcurrentHashMap()
    var cachedBaseEvent: ConcurrentHashMap<Class<out BaseMetricsEventV2>, BaseMetricsEventV2> =
        ConcurrentHashMap()
    var cachedExtraEvent: ConcurrentHashMap<Class<out BaseMetricsEventV2>, BaseMetricsEventV2> =
        ConcurrentHashMap()
    var cachedPostEvent: ConcurrentHashMap<Class<out BaseMetricsEventV2>, BaseMetricsEventV2> =
        ConcurrentHashMap()
    fun onCleared() {
        cachedParams.clear()
        cachedBaseEvent.clear()
        cachedExtraEvent.clear()
    }

    @SuppressLint("CheckResult")
    inline fun <T : BaseMetricsEventV2> postEvent(clazz: Class<T>, type: Int = 0, crossinline initParam: (event: T) -> Unit) {
        Single.fromCallable {
            println("emit before")
            var event = getCacheEvent<T>(clazz,type)
            initParam.invoke(event)
            println("emit after")
            event
        }.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ t ->
                t.post()
                println("subscribe success")
            },{ t ->
                println(t.toString())
            })
//        var event = getCacheEvent<T>(clazz, type)
//        initParam.invoke(event)
//        event.post()
//        saveEvent<T>(clazz, event, type)
    }
    fun <T : BaseMetricsEventV2> getCacheEvent(clazz: Class<T>, type: Int): T {

       // val
        val con = clazz.getConstructor(String::class.java)
        when (type) {
            BASE -> {
                var event = cachedBaseEvent.remove(key = clazz)
                event?.let { it ->
                    return it as T
                } ?: run {
                    return con.newInstance("dasd")
                }
            }
            EXTAR -> {
                var event = cachedBaseEvent.remove(key = clazz)
                event?.let { it ->
                    return it as T
                } ?: run {
                    return con.newInstance("dasd")
                }
            }
            POST -> {
                var event = cachedBaseEvent.remove(key = clazz)
                event?.let { it ->
                    return it as T
                } ?: run {
                    return con.newInstance("dasd")
                }
            }
            else -> return con.newInstance("dasd")
        }
    }

     fun <T : BaseMetricsEventV2> saveEvent(clazz: Class<T>, event: T, type: Int) {
        when (type) {
            BASE -> {
                cachedBaseEvent.put(key = clazz, value = event)
            }
            EXTAR -> {
                cachedExtraEvent.put(key = clazz, value = event)
            }
            POST -> {
                cachedPostEvent.put(key = clazz, value = event)
            }
        }
    }

    val POST = 0
    val BASE = 1
    val EXTAR = 2
}

fun main() {
    println("callback ing1")

    postEvent(LiveCloseWindowEvent::class.java) {
        it.commodityId = "dasfasfa"
        it.anchorId = "dasdasd"
        it.sourcePage = "fafdsfds"
        println("callback ing")
    }
    Thread.sleep(5000)
    println("callback ing5")


}