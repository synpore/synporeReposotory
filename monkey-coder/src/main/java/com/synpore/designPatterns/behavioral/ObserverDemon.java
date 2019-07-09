package com.synpore.designPatterns.behavioral;

import java.util.Vector;

//Define a one-to-many dependency between objects so that
// when one object changes state, all its dependents are notified and updated automatically.
public class ObserverDemon {
}
 abstract class Subject {
    //定义一个观察者数组
    private Vector<Observer> obsVector = new Vector<Observer>();
    //增加一个观察者
    public void addObserver(Observer o){
        this.obsVector.add(o);
    }
    //删除一个观察者
    public void delObserver(Observer o){
        this.obsVector.remove(o);
    }
    //通知所有观察者
    public void notifyObservers(){
        for(Observer o:this.obsVector){
            o.update(this);
        }
    }
}

class Observer{
    public void update(Subject subject){
        System.out.println("subject has changed");
    }
}