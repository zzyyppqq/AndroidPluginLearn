package com.zyp.plugin.demo

import android.app.Application
import android.app.Dialog
import android.app.Service
import android.view.View
import com.zyp.plugin.core.PluginInstall
import com.zyp.plugin.demo.util.BootstrapClass
import com.zyp.plugin.demo.watcher.ServiceWatcher
import com.zyp.plugin.demo.activity.UnRegisterActivity

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 宿主app中可以不注册清单文件的白名单
        val whiteList = listOf(
            UnRegisterActivity::class.java.name,
        )
        PluginInstall.init(this, whiteList)

        BootstrapClass.exemptAll()
        ServiceWatcher().install()

    }

    companion object {
        lateinit var instance: App
    }
}

fun app() = App.instance