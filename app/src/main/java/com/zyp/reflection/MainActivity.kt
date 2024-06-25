package com.zyp.reflection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zyp.reflection.leak.LeakActivity
import com.zyp.reflection.leak.LeakingService
import com.zyp.reflection.plugin.UnRegisterActivity
import com.zyp.reflection.statictest.StaticReflectionTest
import java.lang.reflect.Field

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
        findViewById<Button>(R.id.btn_reflection).setOnClickListener {
            StaticReflectionTest.main()
        }
        findViewById<Button>(R.id.btn_unregister_activity).setOnClickListener {
            startActivity(Intent(this, UnRegisterActivity::class.java).apply {
                putExtra("info","传递过来的消息--MainActivity")
            })
        }

        findViewById<Button>(R.id.btn_leak_activity).setOnClickListener {
            startActivity(Intent(this@MainActivity, LeakActivity::class.java))
        }

        findViewById<Button>(R.id.btn_leak_service).setOnClickListener {
            startService(Intent(this, LeakingService::class.java))
        }
    }

}

