package by.bstu.vs.stpms.courier_application.model.database.contract;

public enum TableName {
    CUSTOMER("customer"),
    ORDER("orders"),
    ORDERED("ordered"),
    PRODUCT("product"),
    ROLE("role"),
    USER("user"),
    USER_ROLE("user_role");

    private String name;

    TableName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
