package by.bstu.vs.stpms.courier_application.model.database.contract;

public enum RoleType {
    ROLE_COURIER(1, "Courier"),
    ROLE_ADMIN(2, "Admin"),
    ROLE_BASIC(3, "Not verified");

    private final long id;
    private final String formattedName;

    RoleType(long id, String formattedName) {
        this.id = id;
        this.formattedName = formattedName;
    }

    public long getId() {
        return id;
    }

    public String getFormattedName() {
        return formattedName;
    }
}
