package ru.alexgladkov.odyssey_demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OdysseyApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}