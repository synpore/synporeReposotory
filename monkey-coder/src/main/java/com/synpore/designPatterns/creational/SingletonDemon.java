package com.synpore.designPatterns.creational;

/**
 * 1、饿汉模式
 * 2、懒汉模式
 * 3、双重检索模式
 * 4、静态内部类模式
 * 5、枚举类
 */
public class SingletonDemon {

}


class HungerySingleton {

    private static HungerySingleton instance = new HungerySingleton();

    //私有化构造函数
    private HungerySingleton() {
    }

    public static HungerySingleton getInstance() {
        return instance;
    }
}

//synchronized decreased query efficiency
class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
    }

    public synchronized static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}


class DoubleCheckSingleton {

    /**
     * pay attention to key words volatile
     * An implementation of double checked locking of Singleton.
     * Intention is to minimize cost of synchronization and  improve performance,
     * by only locking critical section of code, the code which creates instance of Singleton class.
     * If we don't use volatile,wizard things may happen as belows
     * 1、 see a half initialized instance of Singleton（after order arrange,object initiation can be like this:memory allocate-->reference assignment-->create object）
     * 2、under concurrent circumstance,Object can be created more than once because of memory invisible between different thread;
     */

    private static volatile DoubleCheckSingleton instance;

    private DoubleCheckSingleton() {
    }

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}

//lazyLoad + threadSafe(make use of jvm class load mechanism ) + efficiency ,this method is strong recommended
class InnerStaticClassSingleton {

    private InnerStaticClassSingleton() {
    }

    private static class SingltonHolder {
        private static InnerStaticClassSingleton holderInstance = new InnerStaticClassSingleton();
    }

    public static InnerStaticClassSingleton getInstance() {
        return SingltonHolder.holderInstance;
    }
}

enum Singleton {
    singleton
}




