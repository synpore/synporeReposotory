package com.synpore.designPatterns.structural;

//Bridge pattern is about preferring composition over inheritance. Implementation details
// are pushed from a hierarchy to another object with a separate hierarchy.
public class BridgeDemon {

    public static void main(String[] args) {
        Sword enchantedSword = new Sword(new SoulEatingEnchantment());
        enchantedSword.wield();
        enchantedSword.swing();
        enchantedSword.unwield();


        Hammer hammer = new Hammer(new FlyingEnchantment());
        hammer.wield();
        hammer.swing();
        hammer.unwield();
    }


}

interface Weapon {
    void wield();

    void swing();

    void unwield();

    Enchantment getEnchantment();
}

class Sword implements Weapon {

    private final Enchantment enchantment;

    public Sword(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        System.out.println("The sword is wielded.");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        System.out.println("The sword is swinged.");
        enchantment.apply();
    }

    @Override
    public void unwield() {
        System.out.println("The sword is unwielded.");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }
}

class Hammer implements Weapon {

    private final Enchantment enchantment;

    public Hammer(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        System.out.println("The hammer is wielded.");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        System.out.println("The hammer is swinged.");
        enchantment.apply();
    }

    @Override
    public void unwield() {
        System.out.println("The hammer is unwielded.");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }
}


interface Enchantment {
    void onActivate();

    void apply();

    void onDeactivate();
}

class FlyingEnchantment implements Enchantment {

    @Override
    public void onActivate() {
        System.out.println("The item begins to glow faintly.");
    }

    @Override
    public void apply() {
        System.out.println("The item flies and strikes the enemies finally returning to owner's hand.");
    }

    @Override
    public void onDeactivate() {
        System.out.println("The item's glow fades.");
    }
}

class SoulEatingEnchantment implements Enchantment {

    @Override
    public void onActivate() {
        System.out.println("The item spreads bloodlust.");
    }

    @Override
    public void apply() {
        System.out.println("The item eats the soul of enemies.");
    }

    @Override
    public void onDeactivate() {
        System.out.println("Bloodlust slowly disappears.");
    }
}
