package com.synpore.designPatterns.structural;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Using the proxy pattern, a class represents the functionality of another class.
public class ProxyDemon {
    public static void main(String[] args) {
        WizardTowerProxy proxy = new WizardTowerProxy(new IvoryTower());
        proxy.enter(new Wizard("Red wizard")); // Red wizard enters the tower.
        proxy.enter(new Wizard("White wizard")); // White wizard enters the tower.
        proxy.enter(new Wizard("Black wizard")); // Black wizard enters the tower.
        proxy.enter(new Wizard("Green wizard")); // Green wizard is not allowed to enter!
        proxy.enter(new Wizard("Brown wizard")); // Brown wizard is not allowed to enter!
    }
}

interface WizardTower {

    void enter(Wizard wizard);
}

class IvoryTower implements WizardTower {

    private static final Logger LOGGER = LoggerFactory.getLogger(IvoryTower.class);

    public void enter(Wizard wizard) {
        LOGGER.info("{} enters the tower.", wizard);
    }

}

class Wizard {

    private final String name;

    public Wizard(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

 class WizardTowerProxy implements WizardTower {

    private static final Logger LOGGER = LoggerFactory.getLogger(WizardTowerProxy.class);

    private static final int NUM_WIZARDS_ALLOWED = 3;

    private int numWizards;

    private final WizardTower tower;

    public WizardTowerProxy(WizardTower tower) {
        this.tower = tower;
    }

    @Override
    public void enter(Wizard wizard) {
        if (numWizards < NUM_WIZARDS_ALLOWED) {
            tower.enter(wizard);
            numWizards++;
        } else {
            LOGGER.info("{} is not allowed to enter!", wizard);
        }
    }
}