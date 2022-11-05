package com.example.abcdialogue.Weibo

import com.example.abcdialogue.Test.TestT

object Test {
    private var specTextViews:MutableList<Int>? = null
        get() {
            if (field == null) {
                field = mutableListOf()
            }
            return field
        }

    private var specNumberText:MutableList<Int>? = null
        get() {
            if (field == null) {
                field = mutableListOf()
            }
            return field
        }

    var willRemoveTextViews:MutableList<Int>? = null
        get() {
            if (field == null) {
                field = mutableListOf()
            }
            return field
        }
}

fun main() {
    println("start----------------")
    TestT.specTextViews?.let{
        it.add(1)
        it.add(2)
        it.add(3)

    }
    TestT.specNumberText?.let{
        it.add(11)
        it.add(22)
        it.add(33)

    }
    TestT.willRemoveTextViews?.let{
        it.add(111)
        it.add(222)
        it.add(333)

    }
    TestT.specNumberText?.clear()
    TestT.specTextViews?.clear()
    TestT.willRemoveTextViews?.clear()


    TestT.specNumberText = null
    TestT.specTextViews = null
    TestT.willRemoveTextViews = null

    println("end------------------")
}