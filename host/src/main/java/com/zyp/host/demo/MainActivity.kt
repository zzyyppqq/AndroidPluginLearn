package com.zyp.host.demo

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.zyp.host.demo.skin.SkinHelper
import com.zyp.plugin.core.PluginInstall.pluginActivityName
import com.zyp.plugin.core.PluginInstall.pluginClassLoader
import com.zyp.plugin.core.PluginInstall.pluginPath
import com.zyp.plugin.core.activity.ainterface.StubInterfaceActivity
import com.zyp.plugin.core.activity.reflect.StubReflectActivity
import com.zyp.plugin.core.broadcast.BroadcastUtils
import com.zyp.plugin.core.service.StubService
import com.zyp.plugin.skin.BaseSkinActivity

class MainActivity : BaseSkinActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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


        //启动插件中服务
        findViewById<Button>(R.id.btn_plugin_start_service).setOnClickListener {
            StubService.startService(this, pluginClassLoader, "com.zyp.plugin.PluginService")
        }
        //停止插件中服务
        findViewById<Button>(R.id.btn_plugin_stop_service).setOnClickListener {
            StubService.stopService(this, pluginClassLoader, "com.zyp.plugin.PluginService")
        }

        //注册插件广播
        findViewById<Button>(R.id.btn_plugin_broadcast_register).setOnClickListener {
            BroadcastUtils.registerBroadcastReceiver(this, pluginClassLoader, "test_plugin_broadcast", "com.zyp.plugin.PluginBroadcastReceiver")
        }

        //取消注册插件广播
        findViewById<Button>(R.id.btn_plugin_broadcast_unregister).setOnClickListener {
            BroadcastUtils.unregisterBroadcastReceiver(this, "test_plugin_broadcast")
        }

        //发送插件广播
        findViewById<Button>(R.id.btn_plugin_send_broadcast).setOnClickListener {
            val intent = Intent()
            intent.action = "test_plugin_broadcast"
            sendBroadcast(intent)
        }

        findViewById<Button>(R.id.btn_plugin_query_provider).setOnClickListener {
            val uri = Uri.parse("content://com.zyp.stubprovider/plugin1")
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.moveToFirst()
            val res = cursor?.getString(0)
            Log.d("TAG","provider query res: $res")
        }

        findViewById<Button>(R.id.btn_plugin_query_provider2).setOnClickListener {
            val uri = Uri.parse("content://com.zyp.stubprovider/plugin2")
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.moveToFirst()
            val res = cursor?.columnNames
            Log.d("TAG","provider query res: ${res.contentToString()}")
        }


        findViewById<Button>(R.id.btn_plugin_skin).setOnClickListener {
            SkinHelper.skin("plugin_skin.skin")
        }

        findViewById<Button>(R.id.btn_plugin_skin_reset).setOnClickListener {
            SkinHelper.skinReset()
        }

    }

}

