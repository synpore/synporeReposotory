package com.synpore.designPatterns.creational;

/**
 * The abstract factory pattern provides a way to encapsulate a group of individual factories
 * that have a common theme without specifying their concrete classes;
 */
public class AbstractFactoryDemon {


}


interface Castle {
    String getDescription();
}

interface King {
    String getDescription();
}

interface Army {
    String getDescription();
}

// Elven implementations ->
class ElvenCastle implements Castle {
    static final String DESCRIPTION = "This is the Elven castle!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

class ElvenKing implements King {
    static final String DESCRIPTION = "This is the Elven king!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

class ElvenArmy implements Army {
    static final String DESCRIPTION = "This is the Elven Army!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}


// Orcish implementations ->
class OrcishCastle implements Castle {
    static final String DESCRIPTION = "This is the Orcish castle!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

class OrcishKing implements King {
    static final String DESCRIPTION = "This is the Orcish king!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

class OrcishArmy implements Army {
    static final String DESCRIPTION = "This is the Orcish Army!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}


interface KingdomFactory {
    Castle createCastle();

    King createKing();

    Army createArmy();
}

class ElfKingdomFactory implements KingdomFactory {
    public Castle createCastle() {
        return new ElvenCastle();
    }

    public King createKing() {
        return new ElvenKing();
    }

    public Army createArmy() {
        return new ElvenArmy();
    }
}

class OrcKingdomFactory implements KingdomFactory {
    public Castle createCastle() {
        return new OrcishCastle();
    }

    public King createKing() {
        return new OrcishKing();
    }

    public Army createArmy() {
        return new OrcishArmy();
    }
}


