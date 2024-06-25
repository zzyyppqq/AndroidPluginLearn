package com.zyp.plugin

import android.os.Bundle
import com.zyp.plugin.core.activity.ainterface.plugin.BasePluginActivity

class PluginActivity : BasePluginActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)

    }
}