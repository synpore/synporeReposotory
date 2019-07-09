package com.synpore.designPatterns.behavioral;
//Define the skeleton of an algorithm in an operation, deferring some concrete steps to subclasses.
// Template method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.
public class TemplateMethodDemon {

    public static void main(String[] args) {
        AbstractClass tm = new Concrete1Class();
        tm.TemplateMethod();
    }
}

//抽象类
abstract class AbstractClass {
    public void TemplateMethod() //模板方法
    {
        SpecificMethod();
        abstractMethod1();
        System.out.println("something to do");
        abstractMethod2();
    }

    public void SpecificMethod() //具体方法
    {
        System.out.println("抽象类中的具体方法被调用...");
    }

    public abstract void abstractMethod1(); //抽象方法1

    public abstract void abstractMethod2(); //抽象方法2
}

//具体子类
class Concrete1Class extends AbstractClass {
    public void abstractMethod1() {
        System.out.println("抽象方法1的实现1被调用...");
    }

    public void abstractMethod2() {
        System.out.println("抽象方法2的实现1被调用...");
    }
}

//具体子类
class Concrete2Class extends AbstractClass {
    public void abstractMethod1() {
        System.out.println("抽象方法1的实现2被调用...");
    }

    public void abstractMethod2() {
        System.out.println("抽象方法2的实现2被调用...");
    }
}