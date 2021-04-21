package by.bstu.vs.stpms.courier_application.model.database.contract;

public class DbContract {
    public static String getInsertTrigger(TableName tableName) {
        String name = tableName.getName();
        return "create trigger if not exists tr_insert_" + name + " after insert on " + name + " for each row\n" +
                "begin\n" +
                "    insert into changes (tableName, itemId, operation, timeStamp, isUpToDate) values ('" + name + "', new.id, 'insert', strftime('%s', 'now'), 0);\n" +
                "end;";
    }

    public static String getUpdateTrigger(TableName tableName) {
        String name = tableName.getName();
        return "create trigger if not exists tr_update_" + name + " after update on " + name + " for each row\n" +
                "begin\n" +
                "    insert into changes (tableName, itemId, operation, timeStamp, isUpToDate) values ('" + name + "', new.id, 'update', strftime('%s', 'now'), 0);\n" +
                "end;";
    }

    public static String getDeleteTrigger(TableName tableName) {
        String name = tableName.getName();
        return "create trigger if not exists tr_delete_" + name + " after delete on " + name + " for each row\n" +
                "begin\n" +
                "    insert into changes (tableName, itemId, operation, timeStamp, isUpToDate) values ('" + name + "', old.id, 'delete', strftime('%s', 'now'), 0);\n" +
                "end;";
    }
}
