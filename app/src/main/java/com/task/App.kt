package com.task

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.task.data.local.DataStore
import com.task.data.local.SharedPrefs
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Amandeep Chauhan
 */
@HiltAndroidApp
open class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DataStore.init(applicationContext)
        SharedPrefs.init(applicationContext)
        SharedPrefs.getBoolean(PrefKeys.DARK_MODE, false).let {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
