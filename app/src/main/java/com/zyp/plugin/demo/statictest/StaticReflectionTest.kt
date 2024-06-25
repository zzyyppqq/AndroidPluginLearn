package com.zyp.plugin.demo.statictest

import android.util.Log
import java.lang.reflect.Field

object StaticReflectionTest {

    private val info = Info().apply { name = "123" }.attach()

    fun main() {
        try {
            val class1 = Class.forName("com.zyp.reflection.statictest.Info")
            val class2 = Class.forName("com.zyp.reflection.statictest.Info")
            val class3 = Class.forName("com.zyp.reflection.statictest.Info")
            Log.i("ZYPP", "class1: ${class1}, class2: $class2, class3: $class3")

            val info1 = class1.getDeclaredMethod("curInfo").invoke(null)
            val info2 = class2.getDeclaredMethod("curInfo").invoke(null)
            val info3 = class2.newInstance()
            val info4 = class2.getDeclaredConstructor().newInstance()
            Log.i("ZYPP", "info: $info, info1: $info1, info2: $info2, info3: $info3, info4: $info4")

            val name1 = class1.getDeclaredField("name").apply { isAccessible = true }
            val name2 = class2.getDeclaredField("name").apply { isAccessible = true }
            Log.i("ZYPP", "name1: ${name1[info1]}, name2: ${name2[info2]}")
        } catch (e: Throwable) {
            Log.e("ZYPP", Log.getStackTraceString(e))
        }

        test()
    }


    private fun test() {
        val activityThread = Class.forName("android.app.ActivityThread")
        val hclass = Class.forName("android.app.ActivityThread\$H")
        val declaredFields: Array<Field> = hclass.declaredFields
        for (declaredField in declaredFields) {
            Log.i("ZYPP", "declareField: $declaredField")
        }
    }

}