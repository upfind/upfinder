package cn.upfinder.upfinder.Model.DB.Dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.upfinder.upfinder.Model.Bean.Relation;
import cn.upfinder.upfinder.Model.DB.DBHelper;

/**
 * Created by upfinder on 2016/8/19 0019.
 */
public class RelationDao {
    private final String TAG = RelationDao.class.getSimpleName();

    private Context context;
    private Dao<Relation, Integer> relationDaoOpe;
    private DBHelper helper;


    public RelationDao(Context context) {
        this.context = context;

        try {
            helper = DBHelper.getInstance(context);
            relationDaoOpe = helper.getDao(Relation.class);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(TAG, "RelationDao: " + e.getMessage());
        }
    }

    /*
    * 添加一个关系数据
    * */
    public void add(Relation relation) {
        try {
            relationDaoOpe.create(relation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*
    * 查询符合某个条件的关系
    * */
    public List<Relation> queryAll() throws SQLException {
        List<Relation> relationList = relationDaoOpe.queryForAll();
        return relationList;
    }
}
