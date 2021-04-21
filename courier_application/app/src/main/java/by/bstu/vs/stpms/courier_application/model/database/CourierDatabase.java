package by.bstu.vs.stpms.courier_application.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import by.bstu.vs.stpms.courier_application.model.database.contract.DbContract;
import by.bstu.vs.stpms.courier_application.model.database.contract.TableName;
import by.bstu.vs.stpms.courier_application.model.database.dao.ChangeDao;
import by.bstu.vs.stpms.courier_application.model.database.dao.ProductDao;
import by.bstu.vs.stpms.courier_application.model.database.entity.*;

@Database(entities = { Change.class, Customer.class, Order.class, Ordered.class, Product.class, Role.class, User.class, UserRole.class },
        version = 17)
public abstract class CourierDatabase extends RoomDatabase {

    public abstract ProductDao getProductDao();
    public abstract ChangeDao getChangeDao();
//    public abstract SubjectDao getSubjectDao();
//    public abstract TermDao getTermDao();

    private static volatile CourierDatabase INSTANCE;

    public static CourierDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourierDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CourierDatabase.class, "courier_database")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
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

    private static void createTriggers(SupportSQLiteDatabase db) {
        for (TableName tableName: TableName.values()) {
            try {
                db.execSQL(DbContract.getInsertTrigger(tableName));
                db.execSQL(DbContract.getUpdateTrigger(tableName));
                db.execSQL(DbContract.getDeleteTrigger(tableName));
            } catch(SQLiteException e) {
                e.printStackTrace();
            }
        }
    }
}