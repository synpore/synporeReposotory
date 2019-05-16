package com.synpore.Java8Characteristic;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BuildInFunctionalInterfaces {

    public static void main(String[] args) {


        Predicate<Integer> isPositive = (n) -> n != null && n > 0;
        System.out.println(isPositive.test(-7));//false
        System.out.println(isPositive.test(9)); //true

        Predicate<Object> nonNull = Objects::nonNull;
        System.out.println(nonNull.test(null));//false
        System.out.println(nonNull.test(new Object())); //true

        Function<String, Integer> toInteger = Integer::valueOf;
        System.out.println(toInteger.apply("123"));

        //Suppliers produce a result of a given generic type. Unlike Functions, Suppliers don't accept arguments.
        Supplier<Person> personSupplier = Person::new;
        personSupplier.get();   // new Person

        Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.getName());
        greeter.accept(new Person("Luke", 18));

        Comparator<Person> comparator=(p1,p2)->p1.getAge()-p2.getAge();
        Person person1=new Person("Lili",8);
        Person person2=new Person("Lucy",10);
        System.out.println(comparator.compare(person1,person2));


    }
}

class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }
}
