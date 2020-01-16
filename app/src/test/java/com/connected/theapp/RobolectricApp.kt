package com.connected.theapp

import org.koin.core.context.stopKoin

class RobolectricApp : App() {

    override fun setupKoin() {
        stopKoin()
        super.setupKoin()
    }

    companion object {
        private var isCalled = false
    }

}