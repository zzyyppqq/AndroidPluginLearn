

# Java自定义类加载器

[Java基础- 自定义类加载器-CSDN博客](https://blog.csdn.net/weixin_43844521/article/details/134710377)

### 使用场景
自定义类加载器：在创建自定义类加载器时，通常需要重写 findClass 方法。这允许开发者定义自己的类查找逻辑，比如从特定的文件路径、数据库或其他来源加载类。
插件和模块化系统：在实现插件或模块化系统时，findClass 方法可以被用来从模块化的、分离的资源中加载类。

### 实现注意事项
- 重写 findClass：在自定义类加载器中，通常需要重写 findClass 方法以提供新的类查找和加载机制。
- 调用 defineClass：在 findClass 方法中，一旦类的字节码被找到和加载，通常会调用 ClassLoader 类的 defineClass 方法来将字节码转换成 Class 对象。
- 异常处理：如果 findClass 无法找到或加载类，它应该抛出 ClassNotFoundException。

### 总结
在 Java 中，当使用 ClassLoader 类的 loadClass 方法加载一个类时，loadClass 方法内部会按照一定的逻辑来决定如何加载这个类。如果这个类之前没有被加载过，loadClass 方法会最终调用 findClass 方法来加载这个类。这是 ClassLoader 类的内部机制，因此在使用 loadClass 方法时，不需要直接调用 findClass 方法。下面是这个过程的简化描述：

调用 loadClass 方法：当我们在 main 函数中调用 myClassLoader.loadClass("your_class_name") 时，实际上是调用了 ClassLoader 类的 loadClass 方法。

检查类是否已加载：loadClass 方法首先检查这个类是否已经被加载过。如果已经加载，它会直接返回这个类的 Class 对象。

委托给父类加载器：如果类还没有被加载，loadClass 方法会尝试让父类加载器去加载这个类。如果父类加载器不存在或无法加载该类，loadClass 方法会调用 findClass 方法。

调用 findClass 方法：findClass 方法是一个 protected 方法，通常在自定义类加载器中被重写。它包含了从特定来源（如文件系统、网络等）加载类的具体逻辑。

返回 Class 对象：一旦 findClass 方法成功加载了类，并返回了相应的 Class 对象，loadClass 方法就会将这个 Class 对象返回给调用者。

一般在自定义类加载器的实现中，MyClassLoader 类会重写 findClass 方法以从特定路径加载类文件。当我们在 main 方法中调用 loadClass 时，如果 your_class_name 类之前未被加载，MyClassLoader 的 loadClass 方法会间接调用我们重写的 findClass 方法来加载这个类。这就是为什么在 main 函数中并没有显式调用 findClass 方法，但该方法仍然被执行的原因。
