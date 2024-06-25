package com.zyp.plugin

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.zyp.plugin.core.activity.ainterface.plugin.BasePluginActivity
import com.zyp.plugin.R

class PluginActivity : BasePluginActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)

        findViewById<TextView>(R.id.tv_plugin_info).setOnClickListener {
            Toast.makeText(this, "plugin activity", Toast.LENGTH_SHORT).show()
        }
    }
}