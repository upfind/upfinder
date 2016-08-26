package cn.upfinder.upfinder;

import android.test.AndroidTestCase;
import android.util.Log;

import junit.framework.Assert;

import java.sql.SQLException;
import java.util.List;

import cn.upfinder.upfinder.Model.Bean.NewFriend;
import cn.upfinder.upfinder.Model.DB.Dao.NewFriendDao;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class OrmLiteDbTest extends AndroidTestCase {
    private final String TAG = OrmLiteDbTest.class.getSimpleName();

    public void testAddRelation() {
        NewFriend newFriend = new NewFriend("001", NewFriend.ASK_ADD_ME);
        new NewFriendDao(getContext()).add(newFriend);
    }

    public void getRelation() {
        try {
            List<NewFriend> newFriendList = new NewFriendDao(getContext()).queryAll();
            Log.d(TAG, "getRelation: 查询关系类缓存" + newFriendList.size());
            Assert.assertEquals(0, newFriendList.size());
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "getRelation: " + e.getMessage());
        }
    }
}
