package com.zyp.reflection

import android.app.Application
import android.app.Dialog
import android.app.Service
import android.view.View
import com.zyp.reflection.hook.PluginInstall
import com.zyp.reflection.util.BootstrapClass
import com.zyp.reflection.watcher.ServiceWatcher

class App : Application() {

    val leakedViews = mutableListOf<View>()
    val leakedDialogs = mutableListOf<Dialog>()
    val leakedServices = mutableListOf<Service>()

    override fun onCreate() {
        super.onCreate()
        instance = this

        PluginInstall.init(this)

        BootstrapClass.exemptAll()
        ServiceWatcher().install()

    }

    companion object {
        lateinit var instance: App
    }
}

fun app() = App.instance