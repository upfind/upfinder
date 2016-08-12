package cn.upfinder.upfinder.Model.DB.Dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.upfinder.upfinder.Model.Bean.NewFriend;
import cn.upfinder.upfinder.Model.DB.DBHelper;

/**
 * Created by upfinder on 2016/8/12 0012.
 */
public class NewFriendDao {

    private Context context;
    private Dao<NewFriend, Integer> newFriendDao;
    private DBHelper helper;

    public NewFriendDao(Context context) {
        this.context = context;
        try {
            helper = DBHelper.getInstance(context);
            newFriendDao = helper.getDao(NewFriend.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * 查询所有新加好友数据*/
    public List<NewFriend> query() throws SQLException {
        List<NewFriend> newFriendList = newFriendDao.queryForAll();
        return newFriendList;
    }
}
