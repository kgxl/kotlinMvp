package com.zjy.kotlinnote.koitlinnote

import android.app.Application
import android.content.Context
import org.litepal.LitePal

/**
 * Created by zjy on QE.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
        context=applicationContext
    }

    companion object {

        /**
         * 得到上下文
         */
        var context: Context? = null
            private set
    }
}