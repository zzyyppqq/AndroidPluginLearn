package com.zyp.reflection.hook

import android.content.Context
import com.zyp.reflection.app
import dalvik.system.DexClassLoader
import java.io.File

object PluginInstall {

    private lateinit var pluginClassLoader: DexClassLoader

    /**
     *  加载插件
     */
    fun init(context: Context) {
        extractPlugin(context)

        var pluginPath = File(context.filesDir.absolutePath, "plugin.apk").absolutePath

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
        HookInstrumentation.hook(
            app(), PluginContext(
                pluginPath, context, app(), pluginClassLoader
            )
        )
    }

    private fun extractPlugin(context: Context) {
        var inputStream = context.assets.open("plugin.apk")
        File(context.filesDir.absolutePath, "plugin.apk").writeBytes(inputStream.readBytes())
    }

}