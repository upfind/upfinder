package cn.upfinder.upfinder.Model.DB.Dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.upfinder.upfinder.Model.Bean.NewFriend;
import cn.upfinder.upfinder.Model.DB.DBHelper;

/**
 * Created by upfinder on 2016/8/19 0019.
 */
public class NewFriendDao {
    private final String TAG = NewFriendDao.class.getSimpleName();

    private Context context;
    private Dao<NewFriend, Integer> relationDaoOpe;
    private DBHelper helper;


    public NewFriendDao(Context context) {
        this.context = context;

        try {
            helper = DBHelper.getInstance(context);
            relationDaoOpe = helper.getDao(NewFriend.class);
//            relationDaoOpe = helper.getRelationDao();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "NewFriendDao: " + e.getMessage());
        }
    }

    /*
    * 添加一个关系数据
    * */
    public void add(NewFriend newFriend) {

        try {
            relationDaoOpe.create(newFriend);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*
    * 查询符合某个条件的关系
    * */
    public List<NewFriend> queryAll() throws SQLException {
        List<NewFriend> newFriendList = relationDaoOpe.queryForAll();
        return newFriendList;
    }
}
