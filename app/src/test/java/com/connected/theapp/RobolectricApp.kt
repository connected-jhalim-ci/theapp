package com.connected.theapp

class RobolectricApp : App() {

    override fun onCreate() {
        if (!isCalled) super.onCreate()
        isCalled = true
    }

    companion object {
        private var isCalled = false
    }

}