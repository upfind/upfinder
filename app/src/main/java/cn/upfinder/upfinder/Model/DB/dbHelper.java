package cn.upfinder.upfinder.Model.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cn.upfinder.upfinder.Model.Bean.Contacts;
import cn.upfinder.upfinder.Model.Bean.NewFriend;

/**
 * Created by upfinder on 2016/8/10 0010.
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private final String TAG = DBHelper.class.getSimpleName();
    private static final String TABLE_NAME = "sl-upfinder.db";
    private Map<String, Dao> daos = new HashMap<>();

    private static DBHelper instance;


    //单例获取Helper
    public static synchronized DBHelper getInstance(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }




    //获得contactDao
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        Log.d(TAG, "onCreate: 创建SQLLite数据库");
        try {
            TableUtils.createTable(connectionSource, Contacts.class);
            TableUtils.createTable(connectionSource, NewFriend.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

        Log.d(TAG, "onUpgrade: 数据库升级" + i + "//" + i1);
        try {
            TableUtils.dropTable(connectionSource, Contacts.class, true);
            TableUtils.dropTable(connectionSource, NewFriend.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //释放资源
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
