package com.synpore.function;

import java.util.function.Supplier;

public class Condition {

    private Supplier<Boolean> conditionSupplier;

    private Condition(Supplier<Boolean> conditionSupplier) {
        this.conditionSupplier = conditionSupplier;
    }

    public static Condition onCondition(Supplier<Boolean> supplier){
        return new Condition(supplier);
    }

    public void then(VoidFunction function){
        if(conditionSupplier.get()){
            function.doIt();
        }
    }

    @FunctionalInterface
    public  interface VoidFunction {
        void doIt();
    }

    public static void main(String[] args) {
        Condition.onCondition(()->"1".equals("1")).then(()-> System.out.println("Im OK"));
    }
}
