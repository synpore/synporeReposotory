package com.synpore.multiThread;

import java.util.Scanner;
/**
 * 
 * @author      hurenxian  
 * @desription  同步字符串相关的问题
 * @date 2018/12/10 下午3:14
 */  
public class SynchronizeString {


    private static void remoteCall(String aaa) throws InterruptedException {
        System.out.println("真正的内存地址1：" + System.identityHashCode(aaa));
        aaa = aaa.intern();//intern后的字符串才会取是常量池中的同一个内存地址
        System.out.println("真正的内存地址2：" + System.identityHashCode(aaa));
        System.out.println("进入方法：" + Thread.currentThread().getName() + "数据为：" + aaa + " hashcode：" + aaa.hashCode() + " 执行时间：" + System.currentTimeMillis());
        synchronized (aaa) {
            System.out.println("进入锁内：" + Thread.currentThread().getName() + "数据为：" + aaa + " hashcode：" + aaa.hashCode() + " 执行时间：" + System.currentTimeMillis());
            Thread.sleep(15000);
        }
        System.out.println("退出锁：" + Thread.currentThread().getName() + "数据为：" + aaa + " hashcode：" + aaa.hashCode() + " 执行时间：" + System.currentTimeMillis());
    }

    public static void main(String... args) throws Exception {
        new Thread() {
            public void run() {
                try {
                    remoteCall("102017071807514199868487451");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ;
        }.start();

        new Thread() {
            public void run() {
                try {
                    Scanner scanner = new Scanner(System.in);
                    remoteCall(scanner.nextLine());
                    scanner.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

}