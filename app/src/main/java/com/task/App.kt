package com.task

import android.app.Application
import com.task.data.local.DataStore
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Amandeep Chauhan
 */
@HiltAndroidApp
open class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DataStore.init(applicationContext)
    }
}
