package com.synpore.designPatterns.behavioral;

//Allow an object to alter its behavior when its internal state changes.
public class StateDemon {


    public static void main(String[] args) {
        Person person = new Person(State.PEACEFUL);
        person.onSetback();
        //behavior changes as state changed
        person.changeState(State.ANGRY);
        person.onSetback();
    }

}

class Person {
    State state;
    int age;
    String name;

    public Person() {
    }

    public Person(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void onSetback() {
        switch (state) {
            case ANGRY:
                System.out.println("I cant bear this,bad to die");
                break;
            case PEACEFUL:
                System.out.println("It's an little thing,I can fix it out");
                break;
        }
    }
}

enum State {
    ANGRY,
    PEACEFUL;
}