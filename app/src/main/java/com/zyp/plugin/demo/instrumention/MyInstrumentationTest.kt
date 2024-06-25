package com.zyp.plugin.demo.instrumention

import android.app.Activity
import android.app.Instrumentation
import android.util.Log

object MyInstrumentationTest {

    fun test() {
        // hook实现静态代理
        // hook本MainActivity实例的mInstrumentation
        // 替换为HookInstrumentation
        try {
            val activityClass = Activity::class.java
            val instrumentationField = activityClass.getDeclaredField("mInstrumentation").apply { isAccessible = true }
            val mInstrumentation = instrumentationField[this]
            Log.i("ZYPP", "mInstrumentation: $mInstrumentation")

            val activityThreadClass = Class.forName("android.app.ActivityThread")
            val method = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentActivityThread").apply { isAccessible = true }
            val activityThread = method.invoke(null)
            val instrumentationField2 = activityThreadClass.getDeclaredField("mInstrumentation").apply { isAccessible = true }
            val mInstrumentation2 = instrumentationField2[activityThread]
            Log.i("ZYPP", "mInstrumentation2: $mInstrumentation2")

            instrumentationField[this] =
                MyInstrumentation(
                    mInstrumentation as Instrumentation
                )

            Log.i("ZYPP", "Activity mInstrumentation: ${instrumentationField[this]}, mBase: ${(instrumentationField[this] as MyInstrumentation).mBase}")

        } catch (e: Throwable) {
            Log.i("ZYPP", Log.getStackTraceString(e))
        }
    }
}