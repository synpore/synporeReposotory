package com.synpore.designPatterns.structural;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumMap;
import java.util.Map;
//It is used to minimize memory usage or computational expenses by sharing as much as possible with similar objects.
public class FlyweightDemon {

    public static void main(String[] args) {
        PotionFactory factory = new PotionFactory();
        factory.createPotion(PotionType.INVISIBILITY).drink(); // You become invisible. (Potion=6566818)
        factory.createPotion(PotionType.HEALING).drink(); // You feel healed. (Potion=648129364)
        factory.createPotion(PotionType.INVISIBILITY).drink(); // You become invisible. (Potion=6566818)
        factory.createPotion(PotionType.HOLY_WATER).drink(); // You feel blessed. (Potion=1104106489)
        factory.createPotion(PotionType.HOLY_WATER).drink(); // You feel blessed. (Potion=1104106489)
        factory.createPotion(PotionType.HEALING).drink(); // You feel healed. (Potion=648129364)

    }
}

interface Potion {
    void drink();
}

class HealingPotion implements Potion {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealingPotion.class);

    @Override
    public void drink() {
        LOGGER.info("You feel healed. (Potion={})", System.identityHashCode(this));
    }
}

class HolyWaterPotion implements Potion {
    private static final Logger LOGGER = LoggerFactory.getLogger(HolyWaterPotion.class);

    @Override
    public void drink() {
        LOGGER.info("You feel blessed. (Potion={})", System.identityHashCode(this));
    }
}

class InvisibilityPotion implements Potion {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvisibilityPotion.class);

    @Override
    public void drink() {
        LOGGER.info("You become invisible. (Potion={})", System.identityHashCode(this));
    }
}

 class PotionFactory {

    private final Map<PotionType, Potion> potions;

    public PotionFactory() {
        potions = new EnumMap<>(PotionType.class);
    }

    Potion createPotion(PotionType type) {
        Potion potion = potions.get(type);
        if (potion == null) {
            switch (type) {
                case HEALING:
                    potion = new HealingPotion();
                    potions.put(type, potion);
                    break;
                case HOLY_WATER:
                    potion = new HolyWaterPotion();
                    potions.put(type, potion);
                    break;
                case INVISIBILITY:
                    potion = new InvisibilityPotion();
                    potions.put(type, potion);
                    break;
                default:
                    break;
            }
        }
        return potion;
    }
}

enum PotionType {
    HEALING, HOLY_WATER, INVISIBILITY;
}