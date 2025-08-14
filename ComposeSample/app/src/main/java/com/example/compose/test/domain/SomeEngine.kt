package com.example.compose.test.domain

import android.util.Log
import javax.inject.Inject

class SomeEngine @Inject constructor() {

    @Inject
    lateinit var dep1: Dep1

    @Inject
    lateinit var dep2: Dep2

    fun doWork() {
        dep1.doSmth()
        dep2.doSmth()
    }

}

class Dep1 @Inject constructor() {

    fun doSmth() {
        Log.i("", "Dep1::doSmth: working...")
    }
}

class Dep2 @Inject constructor() {

    fun doSmth() {
        Log.i("", "Dep2::doSmth: working...")
    }
}

interface EngineBase {
    fun work()
}

class ComplexEngine(var someEngine: SomeEngine) : EngineBase {

    override fun work() {
        someEngine.doWork()
    }

}