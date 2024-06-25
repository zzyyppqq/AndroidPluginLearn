package com.zyp.lib.classInstance;

public class ClassInstance {

    public static void main(String[] args) throws ClassNotFoundException {
        //方式一：调用运行时类的属性：.class
        Class<Person> clazz1 = Person.class;
        System.out.println(clazz1);//class cn.bruce.java.Person

        //方式二：通过运行时类的对象,调用getClass()
        Person p1 = new Person();
        Class<? extends Person> clazz2 = p1.getClass();
        System.out.println(clazz2);//class cn.bruce.java.Person

        //方式三：调用Class的静态方法：forName(String classPath)
        Class<?> clazz3 = Class.forName("com.zyp.lib.classInstance.Person");
        System.out.println(clazz3);//class cn.bruce.java.Person

        System.out.println(clazz1 == clazz2);//true
        System.out.println(clazz1 == clazz3);//true
        //方式四：使用类的加载器：ClassLoader  (了解)
        ClassLoader classLoader = ClassInstance.class.getClassLoader();
        Class<?> clazz4 = classLoader.loadClass("com.zyp.lib.classInstance.Person");
        System.out.println(clazz4);//class cn.bruce.java.Person
        System.out.println(clazz1 == clazz4);//true
    }
}
