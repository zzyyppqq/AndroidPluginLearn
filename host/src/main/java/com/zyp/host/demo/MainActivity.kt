package com.zyp.host.demo

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zyp.host.core.activity.reflect.StubReflectActivity
import com.zyp.host.core.PluginInstall.pluginActivityName
import com.zyp.host.core.PluginInstall.pluginPath
import com.zyp.host.core.activity.ainterface.StubInterfaceActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //MyInstrumentationTest.test()

        initListener()
    }

    private fun initListener() {
        findViewById<Button>(R.id.btn_unregister_activity).setOnClickListener {
            startActivity(Intent(this, UnRegisterActivity::class.java).apply {
                putExtra("info","传递过来的消息--MainActivity")
            })
        }
        findViewById<Button>(R.id.btn_plugin_activity_by_hook).setOnClickListener {
            var intent = Intent().apply {
                component = ComponentName("com.zyp.plugin", pluginActivityName)
            }
            startActivity(intent)
        }
        findViewById<Button>(R.id.btn_plugin_activity_by_interface).setOnClickListener {
            StubInterfaceActivity.startPluginActivity(this, pluginPath, pluginActivityName)
        }
        findViewById<Button>(R.id.btn_plugin_activity_by_reflection).setOnClickListener {
            StubReflectActivity.startPluginActivity(this, pluginPath, pluginActivityName)
        }

    }

}

