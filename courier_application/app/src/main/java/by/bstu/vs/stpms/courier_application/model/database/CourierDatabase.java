package by.bstu.vs.stpms.courier_application.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import by.bstu.vs.stpms.courier_application.model.database.contract.DbContract;
import by.bstu.vs.stpms.courier_application.model.database.contract.RoleType;
import by.bstu.vs.stpms.courier_application.model.database.contract.TableName;
import by.bstu.vs.stpms.courier_application.model.database.dao.*;
import by.bstu.vs.stpms.courier_application.model.database.entity.*;

@Database(entities = { Change.class, Customer.class, Order.class, Ordered.class, Product.class, Role.class, User.class, UserRole.class },
        version = 23)
public abstract class CourierDatabase extends RoomDatabase {


    public abstract ChangeDao getChangeDao();
    public abstract CustomerDao getCustomerDao();
    public abstract OrderDao getOrderDao();
    public abstract OrderedDao getOrderedDao();
    public abstract ProductDao getProductDao();
    public abstract RoleDao getRoleDao();
    public abstract UserDao getUserDao();
    public abstract UserRoleDao getUserRoleDao();

    private static volatile CourierDatabase INSTANCE;

    public static CourierDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourierDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CourierDatabase.class, "courier_database")
                            .allowMainThreadQueries()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    createRoles(db);
                                    createTriggers(db);
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public void clear() {
        clearAllTables();
        for (RoleType roleType: RoleType.values()) {
            getRoleDao().insert(new Role(roleType));
        }
    }

    private static void createRoles(SupportSQLiteDatabase db) {
        for (RoleType roleType: RoleType.values()) {
            db.execSQL(DbContract.getInsertRole(roleType));
        }
    }

    //TODO sync only for order and user, so other not needed?
    private static void createTriggers(SupportSQLiteDatabase db) {
        for (TableName tableName: TableName.values()) {
            try {
                db.execSQL(DbContract.getInsertTriggerIfFirst(tableName));
                db.execSQL(DbContract.getInsertTriggerIfNotFirst(tableName));
                db.execSQL(DbContract.getUpdateTriggerIfFirst(tableName));
                db.execSQL(DbContract.getUpdateTriggerIfNotFirst(tableName));
                db.execSQL(DbContract.getDeleteTriggerIfFirst(tableName));
                db.execSQL(DbContract.getDeleteTriggerIfNotFirst(tableName));
            } catch(SQLiteException e) {
                e.printStackTrace();
            }
        }
    }
}