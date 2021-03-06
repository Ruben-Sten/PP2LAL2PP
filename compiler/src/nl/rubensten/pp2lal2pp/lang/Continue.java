package nl.rubensten.pp2lal2pp.lang;

import nl.rubensten.pp2lal2pp.IDManager;

/**
 * @author Ruben Schellekens
 */
public class Continue implements Identifyable, Element {

    /**
     * The unique ID of the return statement.
     */
    private int id;

    public Continue() {
        this.id = IDManager.newId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Value getValue() {
        return Value.EMPTY;
    }

}
