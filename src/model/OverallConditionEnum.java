package model;


/**
 * The different types of water conditions
 */
public enum OverallConditionEnum {
    SAFE("Safe"), TREATABLE("Treatable"), UNSAFE("Unsafe");

    private final String stringRep;

    OverallConditionEnum(String stringRep) {
        this.stringRep  = stringRep;
    }

    @Override
    public String toString() {
        return stringRep;
    }
}
