package com.synpore.baseSource;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

//generic use of base class and subclass
public class TypeAutoUpCanKeepPreviousFeatureTest<T extends Dog> extends FatherClass<T> {


    public void  getConcreteAnimals(List<T> animals) {
        System.out.println(animals.get(0).getColor());
    }

    public static void main(String[] args) {
        List<Dog> list = new ArrayList<>();
        list.add(Dog.builder().type("哈士奇").color("白色").build());
        list.add(Dog.builder().type("藏獒").color("红色").build());
        TypeAutoUpCanKeepPreviousFeatureTest<Dog> test=new TypeAutoUpCanKeepPreviousFeatureTest<>();
        test.getConcreteAnimals(list);
    }

}

abstract class FatherClass<T extends Animal> {

    protected List<T> getAnimals(List<T> dogs) {
        return dogs.stream().map(Function.identity()).collect(Collectors.toList());
    }

    protected void getConcreteAnimals(List<T> animals) {
    }
    protected void test(List<T> dogs ){
        getConcreteAnimals(getAnimals(dogs));
    }
}

abstract class Animal {
    int age;

    public Animal() {
    }

    public Animal(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

@Builder
@Data
class Dog extends Animal {
    String type;
    String color;

}