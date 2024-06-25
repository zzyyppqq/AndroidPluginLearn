package com.zyp.host.demo

import android.app.Application
import com.zyp.plugin.core.PluginInstall

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 宿主app中可以不注册清单文件的白名单
        val whiteList = listOf(
            UnRegisterActivity::class.java.name,
        )
        PluginInstall.init(this, whiteList)
    }

    companion object {
        lateinit var instance: App
    }
}

fun app() = App.instance