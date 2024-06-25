package com.zyp.plugin.demo.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zyp.plugin.demo.R


class UnRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unregister)

        findViewById<TextView>(R.id.tv_info).text = intent.getStringExtra("info")
    }
}