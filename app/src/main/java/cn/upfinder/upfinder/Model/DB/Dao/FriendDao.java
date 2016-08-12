package cn.upfinder.upfinder.Model.DB.Dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.Model.DB.DBHelper;

/**
 * Created by upfinder on 2016/8/12 0012.
 */
public class FriendDao {
    private final String TAG = FriendDao.class.getSimpleName();

    private Context context;
    private Dao<Friend, Integer> friendDaoOpe;
    private DBHelper helper;

    public FriendDao(Context context) {
        this.context = context;

        try {
            helper = DBHelper.getInstance(context);
            friendDaoOpe = helper.getDao(Friend.class);
            Log.d(TAG, "FriendDao: 创建friendDaoOpe成功");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "FriendDao: " + e.getMessage());
        }
    }


    /*
    * 添加一个联系人
    * */
    public void addFriend(Friend friend) {
        try {
            friendDaoOpe.create(friend);
            Log.d(TAG, "addFriend: 成功缓存联系人");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "addFriend: " + e.getMessage());
        }
    }

    /*
    * 删除一个联系人
    * */
    public void delFriend(Friend friend) {
        try {
            friendDaoOpe.delete(friend);
            Log.d(TAG, "delFriend: 成功删除缓存联系人");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "delFriend: " + e.getMessage());
        }
    }

    /*
    * 查询所有联系人
    * */
    public List<Friend> queryAll() {
        List<Friend> friendList = null;
        try {
            friendList = friendDaoOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "queryAll: " + e.getMessage());
        }
        return friendList;
    }
}
