package com.example.uaspam

import android.app.Application
import com.example.uaspam.repository.AppContainer
import com.example.uaspam.repository.PertanianContainer

class AgricApplications: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = PertanianContainer()
    }
}
