package com.zyp.host

import android.os.Bundle
import com.zyp.host.core.activity.ainterface.plugin.BasePluginActivity

class PluginActivity : BasePluginActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)

    }
}