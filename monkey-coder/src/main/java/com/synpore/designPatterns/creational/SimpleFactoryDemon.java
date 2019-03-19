package com.synpore.designPatterns.creational;


/**
 * simple factory is not stick to open close principle,it's necessary to modify factory class when add a new series BMW car;
 */
public class SimpleFactoryDemon {

    public static void main(String[] args) {
        BmwFactory factory = new BmwFactory();
        BMWCar car1=factory.createBmwCar(1);
        System.out.println(car1.getSeriesName());

        BMWCar car2=factory.createBmwCar(2);
        System.out.println(car2.getSeriesName());

        BMWCar car3=factory.createBmwCar(3);
        System.out.println(car3.getSeriesName());

    }

}


class BmwFactory {

    BMWCar createBmwCar(int type) {
        switch (type) {
            case 1:
                return new Bmw5L();
            case 2:
                return new BmX3();
            case 3:
                return new BmX5();
            default:
                return null;
        }
    }

}

abstract class BMWCar {
    public String getBrandName() {
        return "BMW";
    }

    public abstract String getSeriesName();
}

class Bmw5L extends BMWCar {
    @Override
    public String getSeriesName() {
        return "5L";
    }
}

class BmX3 extends BMWCar {

    @Override
    public String getSeriesName() {
        return "X3";
    }
}

class BmX5 extends BMWCar {
    @Override
    public String getSeriesName() {
        return "X5";
    }
}



