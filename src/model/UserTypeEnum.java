package model;


/**
 * The different types of users that can use the app
 */
public enum UserTypeEnum {
    USER("user"), WORKER("worker"), MANAGER("manager"), ADMIN("admin");

    private final String databaseRepresentation;

    UserTypeEnum(String databaseRepresentation) {
        this.databaseRepresentation = databaseRepresentation;
    }

    @Override
    public String toString() {
        return databaseRepresentation;
    }
}
