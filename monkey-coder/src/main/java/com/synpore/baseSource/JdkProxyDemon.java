package com.synpore.baseSource;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyDemon {

    public static void main(String[] args) {

        Foo proxyperson=PrintProxy.createProxy(new Person(),Foo.class);
        proxyperson.foo();
    }
}

interface Foo {

    void foo();

}
class  Person implements Foo{

    @Override
    public void foo() {
        System.out.println("what a stupid man");
    }
}


class PrintProxy implements InvocationHandler {

    private Object target;

    public PrintProxy(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before testing for the colors of the feathers");

        method.invoke(target, args);

        System.out.println("After testing for the colors of the feathers");

        return null;
    }

    public static <T> T createProxy(Object t,Class<T> tClass) {
        return (T)Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), new PrintProxy(t));
    }
}
