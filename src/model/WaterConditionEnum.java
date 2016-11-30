package model;


/**
 * The different types of water conditions
 */
public enum WaterConditionEnum {
    WASTE("Waste"), TREATABLEC("Treatable-Clear"),TREATABLEM("Treatable-Muddy"), POTABLE("Potable");

    private final String stringRep;
    WaterConditionEnum(String stringRep) {
        this.stringRep = stringRep;
    }

    @Override
    public String toString() {
        return stringRep;
    }
}
