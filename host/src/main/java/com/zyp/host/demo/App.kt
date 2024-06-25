package com.zyp.host.demo

import android.app.Application
import com.zyp.host.demo.util.BootstrapClass
import com.zyp.plugin.core.PluginInstall
import com.zyp.plugin.skin.SkinInstaller

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        BootstrapClass.exemptAll()

        // 宿主app中可以不注册清单文件的白名单
        val whiteList = listOf(
            UnRegisterActivity::class.java.name,
        )
        PluginInstall.init(this, whiteList)

        SkinInstaller.init(this)
    }

    companion object {
        lateinit var instance: App
    }
}

fun app() = App.instance