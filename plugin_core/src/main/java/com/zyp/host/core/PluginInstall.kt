package com.zyp.host.core

import android.app.Application
import android.content.Context
import com.zyp.host.core.activity.hook.HookInstrumentation
import com.zyp.host.core.activity.hook.PluginContext
import dalvik.system.DexClassLoader
import java.io.File

object PluginInstall {

    private lateinit var pluginClassLoader: DexClassLoader

    open lateinit var pluginPath: String

    open lateinit var pluginActivityName: String

    /**
     *  加载插件
     */
    fun init(context: Application, whiteList: List<String>? = null) {
        extractPlugin(context)

        pluginPath = File(context.filesDir.absolutePath, "plugin.apk").absolutePath

        pluginActivityName = "com.zyp.plugin.PluginActivity"

        //这步不需要，直接在hookInstrumentation中用pluginClassloader生成插件类，不然会起冲突。
//        var loadPluginDexManager = LoadPluginDexManager(this)
//        loadPluginDexManager.loadPlugin(pluginPath) //加载插件的类到本地ClassLoader

        var nativeLibDir = File(context.filesDir, "pluginlib")
        var dexOutPath = File(context.filesDir, "dexout")
        if (!dexOutPath.exists()) {
            dexOutPath.mkdirs()
        }
        pluginClassLoader = DexClassLoader(
            pluginPath,
            dexOutPath.absolutePath,
            nativeLibDir.absolutePath,
            this::class.java.classLoader
        )
        val hookInstrumentation = HookInstrumentation.hook(
            context, PluginContext(
                pluginPath, context, context, pluginClassLoader
            )
        )
        hookInstrumentation.setHostUnRegisterActivityWhiteList(whiteList)
    }

    private fun extractPlugin(context: Context) {
        var inputStream = context.assets.open("plugin.apk")
        File(context.filesDir.absolutePath, "plugin.apk").writeBytes(inputStream.readBytes())
    }

}