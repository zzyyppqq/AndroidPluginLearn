package com.zyp.lib.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//创建继承了InvocationHandler接口的类
public class MyInvocationHandler implements InvocationHandler {
    private Object obj;//需要使用被代理类的对象进行赋值

    public void bind(Object obj) {
        this.obj = obj;
    }

    //当我们通过代理类的对象，调用方法a时，就会自动的调用如下的方法：invoke()
    //将被代理类要执行的方法a的功能就声明在invoke()中
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("--- Proxy Hook start ---");
        //method:即为代理类对象调用的方法，此方法也就作为了被代理类对象要调用的方法
        //obj:被代理类的对象
        Object returnValue = method.invoke(obj, args);

        System.out.println("--- Proxy Hook end ---");
        //上述方法的返回值就作为当前类中的invoke()的返回值。
        return returnValue;
    }

    public interface Human {
        String getBelief();

        void eat(String food);
    }

    //被代理类
    public static class SuperMan implements Human {

        @Override
        public String getBelief() {
            return "I believe I can fly!";
        }

        @Override
        public void eat(String food) {
            System.out.println("I like eat " + food);
        }
    }

    public static void main(String[] args) {
        SuperMan obj = new SuperMan();
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.bind(obj);
        Human proxyInstance = (Human) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);

        //当通过代理类对象调用方法时，会自动的调用被代理类中同名的方法
        String belief = proxyInstance.getBelief();
        System.out.println(belief);
        proxyInstance.eat("火锅");
    }
}
