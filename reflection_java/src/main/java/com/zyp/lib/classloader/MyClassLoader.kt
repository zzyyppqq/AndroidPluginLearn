package com.zyp.lib.classloader

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

/**
 * https://blog.csdn.net/weixin_43844521/article/details/134710377#
 */
class MyClassLoader(private val classPath: String): ClassLoader() {

    override fun findClass(name: String): Class<*> {
        val path = "$classPath/${name.replace('.', '/')}.class"
        if (!File(path).exists()) {
            println("File is not exist [$path]")
            return super.findClass(name)
        }
        val bos = ByteArrayOutputStream()
        // 使用 Files.copy() 方法将文件的内容复制到 ByteArrayOutputStream 对象中
        try {
            Files.copy(Paths.get(path), bos)
            val bytes = bos.toByteArray()
            println("MyClassLoader $this findClass name: $name, path: $path")
            return defineClass(name, bytes, 0, bytes.size)
        }catch (e: IOException) {
            e.printStackTrace()
        }
        return super.findClass(name)
    }
}


fun main() {
    val projectPath = System.getProperty("user.dir")
    val classPath = "$projectPath/java_lib/src/main/resources/classes"
    val name = "com.zyp.lib.classloader.MyClassLoaderTest"
    // 创建自定义类加载器对象
    val myClassLoader = MyClassLoader(classPath)
    val c1 = myClassLoader.loadClass(name)
    val c2 = myClassLoader.loadClass(name)
    println(c1)
    // 判断两次加载获得的类对象是否相同
    println(c1 == c2) // true
    // 即使多次执行loadClass方法，但实际上类文件只会加载一次，第一次加载后就会放在自定义类加载器的缓存中
    // 下次再调用loadClass()时就可以在缓存中找到了，不会重复的进行类加载

    // -------------------------------------------------------
    val myClassLoader1 = MyClassLoader(classPath)
    val c3 = myClassLoader1.loadClass(name)

    // 唯一确定类的方式是，包名 类名相同，而且类加载器也是同一个，才认为这两个类才是完全一致的
    // 虽然MapImpl1与刚才的包名类名一样，但是由于他俩的类加载器对象不是同一个，所以认为这两个类不是同一个类
    // 也就是，这个类会被加载两次，因为是不同的类加载器，就认为他俩是相互隔离的，不会产生冲突
    println(c1 == c3) // false

    // 创建这个类的实例对象会触发静态代码块的执行
    c1.newInstance()  // 会打印出"init..."(在静态代码块中提前写好的)

}