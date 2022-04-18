package com.lonbon.mvidemo.ui

import android.app.Application
import android.content.Context

/**
 * @Description 自定义的application
 * @author liwu
 * @date 2022/4/14-17:05
 */
class DefineApp : Application() {

    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}