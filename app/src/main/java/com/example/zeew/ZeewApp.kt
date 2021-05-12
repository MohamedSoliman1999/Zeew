package com.example.zeew

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZeewApp : Application() {
    companion object {
        private lateinit var  INSTANCE:ZeewApp
        public fun getINSTANCE():ZeewApp{
            if(INSTANCE==null){
                INSTANCE=ZeewApp()
            }
            return INSTANCE
        }
    }
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        INSTANCE = this
    }

}