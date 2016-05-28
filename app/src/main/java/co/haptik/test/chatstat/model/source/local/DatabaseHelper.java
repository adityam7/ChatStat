package co.haptik.test.chatstat.model.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import co.haptik.test.chatstat.model.DaoMaster;
import co.haptik.test.chatstat.model.DaoSession;

/**
 * Created by Aditya Mehta on 28/05/16.
 */
public class DatabaseHelper {
    private static DatabaseHelper sDatabaseHelper;
    private final DaoSession mDaoSession;

    public static DatabaseHelper getInstance(Context context) {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(context);
        }
        return sDatabaseHelper;
    }

    private DatabaseHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "haptik-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void resetDatabase() {
        DaoMaster.dropAllTables(mDaoSession.getDatabase(), true);
        DaoMaster.createAllTables(mDaoSession.getDatabase(), true);
    }
}
