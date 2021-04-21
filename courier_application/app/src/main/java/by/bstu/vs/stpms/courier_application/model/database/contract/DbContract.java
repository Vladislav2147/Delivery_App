package by.bstu.vs.stpms.courier_application.model.database.contract;

public class DbContract {
    public static String getInsertTriggerIfFirst(TableName tableName) {
        String name = tableName.getName();
        return "create trigger if not exists tr_insert_" + name + "_if_first after insert on " + name + " for each row\n" +
                "when ((select count(*) from changes where tableName = '" + name + "' and itemId = new.id) == 0) \n" +
                "begin\n" +
                "    insert into changes (tableName, itemId, operation, timeStamp, isUpToDate) values ('" + name + "', new.id, 'insert', strftime('%s', 'now'), 0);\n" +
                "end;";
    }

    public static String getInsertTriggerIfNotFirst(TableName tableName) {
        String name = tableName.getName();
        return "create trigger if not exists tr_insert_" + name + "_if_not_first after insert on " + name + " for each row\n" +
                "when ((select count(*) from changes where tableName = '" + name + "' and itemId = new.id) > 0) \n" +
                "begin\n" +
                "    update changes\n" +
                "    set \n" +
                "    operation = 'insert',\n" +
                "    timeStamp = strftime('%s', 'now')\n" +
                "    where itemId = new.id;              \n" +
                "end;";
    }

    public static String getUpdateTriggerIfFirst(TableName tableName) {
        String name = tableName.getName();
        return "create trigger if not exists tr_update_" + name + "_if_first after update on " + name + " for each row\n" +
                "when ((select count(*) from changes where tableName = '" + name + "' and itemId = new.id) == 0) \n" +
                "begin\n" +
                "    insert into changes (tableName, itemId, operation, timeStamp, isUpToDate) values ('" + name + "', new.id, 'update', strftime('%s', 'now'), 0);\n" +
                "end;";
    }

    public static String getUpdateTriggerIfNotFirst(TableName tableName) {
        String name = tableName.getName();
        return "create trigger if not exists tr_update_" + name + "_if_not_first after update on " + name + " for each row\n" +
                "when ((select count(*) from changes where tableName = '" + name + "' and itemId = new.id) > 0) \n" +
                "begin\n" +
                "    update changes\n" +
                "    set \n" +
                "    operation = 'update',\n" +
                "    timeStamp = strftime('%s', 'now')\n" +
                "    where itemId = new.id;              \n" +
                "end;";
    }

    public static String getDeleteTriggerIfFirst(TableName tableName) {
        String name = tableName.getName();
        return "create trigger if not exists tr_delete_" + name + "_if_first after delete on " + name + " for each row\n" +
                "when ((select count(*) from changes where tableName = '" + name + "' and itemId = old.id) == 0) \n" +
                "begin\n" +
                "    insert into changes (tableName, itemId, operation, timeStamp, isUpToDate) values ('" + name + "', old.id, 'delete', strftime('%s', 'now'), 0);\n" +
                "end;";
    }

    public static String getDeleteTriggerIfNotFirst(TableName tableName) {
        String name = tableName.getName();
        return "create trigger if not exists tr_delete_" + name + "_if_not_first after delete on " + name + " for each row\n" +
                "when ((select count(*) from changes where tableName = '" + name + "' and itemId = old.id) > 0) \n" +
                "begin\n" +
                "    update changes\n" +
                "    set \n" +
                "    operation = 'delete',\n" +
                "    timeStamp = strftime('%s', 'now')\n" +
                "    where itemId = old.id;              \n" +
                "end;";
    }
}
