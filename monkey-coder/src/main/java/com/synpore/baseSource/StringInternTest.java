package com.synpore.baseSource;

public class StringInternTest {

    public static void main(String[] args) {
        String s1=new String("abc");
        s1.intern();
        String s2="abc";
        System.out.println(s1==s2);

        String s3=new String("ab")+new String("cd");
        String s4="abcd";
        s3.intern();
        System.out.println(s3==s4);

        //jdk1.6 output:false,false;
        //jdk1.6+ output:false,true
    }
}
