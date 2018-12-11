package com.synpore.baseSource;

/**
 * object initiate order,contains all of relevant element
 */
public class ObjectInitiate {


   static class Fater{

       static {
           System.out.println("this is father static code block1");
       }

        public static int fatherStaticVariable1=10;


       public static int fatherStaticVariable2;

       static {
           System.out.println("this is father static code block2,fatherStaticVariable2="+fatherStaticVariable2);

           fatherStaticVariable2=11;
       }

        public int age;

        public String name;

        static {
            System.out.println("this is father static code block2,fatherStaticVariable1="+fatherStaticVariable1+",fatherStaticVariable2="+fatherStaticVariable2);
        }

        {

           System.out.println("this is father normal code block,age="+age);
       }

       public Fater(int age, String name) {
           this.age = age;
           this.name = name;
       }

       public Fater() {
           System.out.println("this is father no args constructor");
       }
   }

   static class Son extends Fater{

       public static int sonStaticVariable=5;

       public String toy;

       static {
           System.out.println("this is son static code block,sonStaticVariable="+sonStaticVariable);
       }
       {

           System.out.println("this is son normal code block,toy="+toy);
       }

       public Son() {
           System.out.println("this is  no args constructor");
       }
   }


    public static void main(String[] args) {
        Son test=new Son();
    }
    /**
     * conclusion:
     * before jvm initiate an object,load class first of all obey father class ahead of son class rule;
     *
     * load class contains static variable initiation and static code block ,execute according code order;
     *
     * then  goes normal variable initiation and normal code block as static action,last call constructor;
     *
     * in brief as below:
     *
     * 1、static variable|(code block）,father ahead of son
     *
     * 2、normal variable|(code block）,father ahead of son
     *
     * 3、constructor,father ahead of son
     *
     */

}
