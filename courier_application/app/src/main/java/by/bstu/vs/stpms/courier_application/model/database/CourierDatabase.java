package by.bstu.vs.stpms.courier_application.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import by.bstu.vs.stpms.courier_application.model.database.dao.ProductDao;
import by.bstu.vs.stpms.courier_application.model.database.entity.*;

@Database(entities = { Change.class, Customer.class, Order.class, Ordered.class, Product.class, Role.class, User.class, UserRole.class }, version = 3)
public abstract class CourierDatabase extends RoomDatabase {

    public abstract ProductDao getProductDao();
//    public abstract SubjectDao getSubjectDao();
//    public abstract TermDao getTermDao();

    private static volatile CourierDatabase INSTANCE;

    public static CourierDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourierDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CourierDatabase.class, "courier_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}