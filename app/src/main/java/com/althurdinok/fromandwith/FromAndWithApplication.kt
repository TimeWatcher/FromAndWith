package com.althurdinok.fromandwith

import dagger.hilt.android.HiltAndroidApp
import android.app.Application
import android.content.Context

@HiltAndroidApp
class FromAndWithApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: Application? = null

        fun getContext(): Context {
            return context!!
        }
    }
}