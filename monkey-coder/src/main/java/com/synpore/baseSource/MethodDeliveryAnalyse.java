package com.synpore.baseSource;

/**
 * 方法传递的引用还是值
 *  传递的是引用的拷贝
 */
public class MethodDeliveryAnalyse {


    static class Student{

        private String name;

        public Student(String name) {
            this.name = name;
        }

        public Student() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

  public static void swap(Student x,Student y){
      Student temp = x;
      x = y;
      y = temp;
      System.out.println("x:" + x.getName());
      System.out.println("y:" + y.getName());
  }

    public static void main(String[] args) {
        Student s1 = new Student("小张");
        Student s2 = new Student("小李");
        //MethodDeliveryAnalyse.swap(s1, s2);
        changeParameter(s1);
        System.out.println("s1:" + s1.getName());
        System.out.println("s2:" + s2.getName());
    }

    public  static void changeParameter(Student student){
        student.setName("changed");
    }
}
