package com.synpore.designPatterns.creational;

/**
 * It provides a way to delegate the instantiation logic to child classes.
 */
public class FactoryMethodDemon {


}

interface BmwCarFactory {
    BMWCar createBmwCar();
}

class Bmw5lFactory implements BmwCarFactory {
    @Override
    public BMWCar createBmwCar() {
        return new Bmw5L();
    }
}

class BwmX3Factory implements BmwCarFactory {
    @Override
    public BMWCar createBmwCar() {
        return new BmX3();
    }
}

class BwmX5Factory implements BmwCarFactory {
    @Override
    public BMWCar createBmwCar() {
        return new BmX5();
    }
}


