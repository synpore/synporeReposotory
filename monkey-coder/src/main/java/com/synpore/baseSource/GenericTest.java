package com.synpore.baseSource;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {

    public static void main(String[] args) {

        //pecs :produce extends, consumer super
        Plate<? extends Fruit> p=new Plate<Apple>(new Apple());
        p=new Plate<Banana>(new Banana());
        Fruit fruit=p.get();
        // p.set(new Apple()); write error
        System.out.println(fruit.getClass());

        Plate<? super Fruit> p2=new Plate<Fruit>(new Fruit());
        p2.set(new Banana());
        p2.set(new Apple());

        Object p2T=p2.get(); //get an Object


        List<Banana> list=new ArrayList<>();
        list.add(new Banana());
        list.add(new Banana());
        List<? extends Fruit> list1=new ArrayList<Banana>(list);

    }

}


class Plate<T> {
    private T item;

    public Plate(T t) {
        item = t;
    }

    public void set(T t) {
        item = t;
    }

    public T get() {
        return item;
    }
}


class Food {
}

class Fruit extends Food {
}

class Meat extends Food {
}

class Apple extends Fruit {
}

class Banana extends Fruit {
}