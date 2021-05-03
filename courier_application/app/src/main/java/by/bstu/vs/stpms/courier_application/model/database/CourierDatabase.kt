package by.bstu.vs.stpms.courier_application.model.database

import android.content.Context
import android.database.sqlite.SQLiteException
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import by.bstu.vs.stpms.courier_application.model.database.contract.DbContract
import by.bstu.vs.stpms.courier_application.model.database.contract.RoleType
import by.bstu.vs.stpms.courier_application.model.database.contract.TableName
import by.bstu.vs.stpms.courier_application.model.database.dao.*
import by.bstu.vs.stpms.courier_application.model.database.entity.*
import java.util.*

@Database(entities = [Change::class, Customer::class, Order::class, Ordered::class, Product::class, Role::class, User::class, UserRole::class],
        version = 27)
abstract class CourierDatabase : RoomDatabase() {
    abstract val changeDao: ChangeDao
    abstract val customerDao: CustomerDao
    abstract val orderDao: OrderDao
    abstract val orderedDao: OrderedDao
    abstract val productDao: ProductDao
    abstract val roleDao: RoleDao
    abstract val userDao: UserDao
    abstract val userRoleDao: UserRoleDao

    suspend fun clear() {
        clearAllTables()
        for (roleType in RoleType.values()) {
            roleDao.insert(Role(roleType))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CourierDatabase? = null
        fun getDatabase(context: Context): CourierDatabase {
            if (INSTANCE == null) {
                synchronized(CourierDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                CourierDatabase::class.java, "courier_database")
                                .allowMainThreadQueries()
                                .addCallback(object : Callback() {
                                    override fun onCreate(db: SupportSQLiteDatabase) {
                                        super.onCreate(db)
                                        createRoles(db)
                                        createTriggers(db)
                                    }
                                })
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }

        private fun createRoles(db: SupportSQLiteDatabase) {
            for (roleType in RoleType.values()) {
                db.execSQL(DbContract.getInsertRole(roleType))
            }
        }

        private fun createTriggers(db: SupportSQLiteDatabase) {
            val clientChangeableTables: MutableList<TableName> = ArrayList()
            clientChangeableTables.add(TableName.ORDER)
            clientChangeableTables.add(TableName.USER)
            for (tableName in clientChangeableTables) {
                try {
                    db.execSQL(DbContract.getInsertTriggerIfFirst(tableName))
                    db.execSQL(DbContract.getInsertTriggerIfNotFirst(tableName))
                    db.execSQL(DbContract.getUpdateTriggerIfFirst(tableName))
                    db.execSQL(DbContract.getUpdateTriggerIfNotFirst(tableName))
                    db.execSQL(DbContract.getDeleteTriggerIfFirst(tableName))
                    db.execSQL(DbContract.getDeleteTriggerIfNotFirst(tableName))
                } catch (e: SQLiteException) {
                    e.printStackTrace()
                }
            }
        }
    }
}