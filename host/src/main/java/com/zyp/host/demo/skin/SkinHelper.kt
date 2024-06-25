package com.zyp.host.demo.skin

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.zyp.host.demo.MainActivity
import com.zyp.host.demo.app
import com.zyp.plugin.skin.SkinManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object SkinHelper {

    fun skin(name: String) {
        // 从服务器上下载
        val copyResult = copyAssetAndWrite(name) // 拷贝到本地缓存
        if(!copyResult){
            Toast.makeText(app(),"没有拷贝成功,确保assets目录下有资源文件", Toast.LENGTH_SHORT).show()
            return
        }
        val skinPath = File(app().cacheDir, name).absolutePath
        Log.i("ZYPP", "skinPath: $skinPath")
        // 换肤
        SkinManager.getInstance().loadSkin(skinPath)
    }

    fun skinReset() {
        // 恢复默认
        SkinManager.getInstance().restoreDefault()
    }


    fun startActivity() {
        // 跳转
        val intent = Intent(app(), MainActivity::class.java)
        app().startActivity(intent)
    }


    private fun copyAssetAndWrite(
        fileName: String
    ): Boolean {
        try {
            val cacheDir = app().cacheDir
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            val outFile = File(cacheDir, fileName)
            if (!outFile.exists()) {
                val res = outFile.createNewFile()
                if (!res) {
                    return false
                }
            } else {
                if (outFile.length() > 10) { //表示已经写入一次
                    return true
                }
            }
            val ins = app().assets.open(fileName)
            val fos = FileOutputStream(outFile)
            val buffer = ByteArray(1024)
            var byteCount: Int
            while (ins.read(buffer).also { byteCount = it } != -1) {
                fos.write(buffer, 0, byteCount)
            }
            fos.flush()
            ins.close()
            fos.close()
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }
}