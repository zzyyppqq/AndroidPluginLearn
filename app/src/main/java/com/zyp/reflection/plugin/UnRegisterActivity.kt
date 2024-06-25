package com.zyp.reflection.plugin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zyp.reflection.R


class UnRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unregister)

        findViewById<TextView>(R.id.tv_info).text = intent.getStringExtra("info")
    }
}