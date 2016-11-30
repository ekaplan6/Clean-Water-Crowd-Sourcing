package model;


/**
 * The different types of water types
 */
public enum WaterTypeEnum {
    BOTTLED("Bottled"), WELL("Well"), STREAM("Stream"), LAKE("Lake"), SPRING("Spring"), OTHER("Other");
    private final String stringRep;

    WaterTypeEnum(String stringRep) {
        this.stringRep = stringRep;
    }

    @Override
    public String toString() {
        return stringRep;
    }
}
