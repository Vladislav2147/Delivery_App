package by.bstu.vs.stpms.courier_application.model.database.contract;

public enum RoleType {
    ROLE_COURIER(1),
    ROLE_ADMIN(2),
    ROLE_BASIC(3);

    private final long id;

    RoleType(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
