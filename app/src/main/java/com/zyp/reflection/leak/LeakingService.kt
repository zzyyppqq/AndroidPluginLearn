package com.zyp.reflection.leak

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.zyp.reflection.app

class LeakingService : Service() {

  override fun onCreate() {
    super.onCreate()
    Log.i("ZYPP", "LeakingService onCreate")
    app().leakedServices += this
    stopSelf()
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    Log.i("ZYPP", "LeakingService onStartCommand")
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onDestroy() {
    super.onDestroy()
    Log.i("ZYPP", "LeakingService onDestroy")
  }
}