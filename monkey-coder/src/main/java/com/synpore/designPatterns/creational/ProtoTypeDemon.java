package com.synpore.designPatterns.creational;

import java.io.*;

public class ProtoTypeDemon {


    public static void main(String[] args) {
        //shallow copy demonstration
        System.out.println("shallow copy demonstration");
        SheepA sheepA1 = new SheepA("多利1", new Stomach(18, true));
        SheepA sheepA2 = sheepA1.clone();
        System.out.println("sheepA1 before:" + sheepA1);
        System.out.println("sheepA2 before:" + sheepA2);
        sheepA1.getStomach().setSize(999);
        sheepA1.setName("多莉莉");
        System.out.println("sheepA1 after:" + sheepA1);
        System.out.println("sheepA2 after:" + sheepA2);

        //deep copy demonstration
        System.out.println("deep copy demonstration");
        SheepB sheepB1 = new SheepB("多利2", new Stomach(18, true));
        SheepB sheepB2 = sheepB1.clone();
        System.out.println("sheepB1 before:" + sheepB1);
        System.out.println("sheepB2 before:" + sheepB2);
        sheepB1.getStomach().setSize(999);
        sheepB1.setName("多莉莉");
        System.out.println("sheepB1 after:" + sheepB1);
        System.out.println("sheepB2 after:" + sheepB2);

        //use serialize and deserialize to implement deep copy
        System.out.println("use serialize and deserialize to implement deep copy");
        try {
            SheepA sheepA3 = new SheepA("多利3", new Stomach(18, true));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(sheepA3);
            byte[] bytes = bos.toByteArray();

            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            SheepA sheepA4 = (SheepA) ois.readObject();  //克隆好的对象
            System.out.println("sheepA3 before:" + sheepA3);
            System.out.println("sheepA4 before:" + sheepA4);
            sheepA3.getStomach().setSize(999);
            sheepA3.setName("多莉莉");
            System.out.println("sheepA3 after:" + sheepA3);
            System.out.println("sheepA4 after:" + sheepA4);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}


//shallow copy
class SheepA implements Cloneable ,Serializable{

    private String name;

    private Stomach stomach;

    public SheepA() {
    }

    public SheepA(String name, Stomach stomach) {
        this.name = name;
        this.stomach = stomach;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stomach getStomach() {
        return stomach;
    }

    public void setStomach(Stomach stomach) {
        this.stomach = stomach;
    }

    @Override
    protected SheepA clone() {
        SheepA cloneResult = null;
        try {
            cloneResult = (SheepA) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloneResult;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("\"name\":");
        stringBuffer.append(this.name);
        stringBuffer.append(",");
        stringBuffer.append("\"stomach\":");
        stringBuffer.append(this.stomach);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

//deep copy
class SheepB implements Cloneable {

    private String name;

    private Stomach stomach;

    public SheepB(String name, Stomach stomach) {
        this.name = name;
        this.stomach = stomach;
    }

    @Override
    protected SheepB clone() {
        SheepB cloneResult = null;
        try {
            cloneResult = (SheepB) super.clone();
            cloneResult.stomach = this.stomach.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloneResult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stomach getStomach() {
        return stomach;
    }

    public void setStomach(Stomach stomach) {
        this.stomach = stomach;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("\"name\":");
        stringBuffer.append(this.name);
        stringBuffer.append(",");
        stringBuffer.append("\"stomach:\"");
        stringBuffer.append(this.stomach);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

class Stomach implements Cloneable,Serializable {

    private int size;
    private boolean isHealth;

    public Stomach(int size, boolean isHealth) {
        this.size = size;
        this.isHealth = isHealth;
    }

    @Override
    protected Stomach clone() throws CloneNotSupportedException {
        Stomach cloneResult = (Stomach) super.clone();
        return cloneResult;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isHealth() {
        return isHealth;
    }

    public void setHealth(boolean health) {
        isHealth = health;
    }

    @Override
    public String toString() {
        String referenceCode = super.toString();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("@");
        stringBuffer.append(referenceCode);
        stringBuffer.append("[");
        stringBuffer.append("\"size\":");
        stringBuffer.append(this.size);
        stringBuffer.append(",");
        stringBuffer.append("\"isHealth\":");
        stringBuffer.append(this.isHealth);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}