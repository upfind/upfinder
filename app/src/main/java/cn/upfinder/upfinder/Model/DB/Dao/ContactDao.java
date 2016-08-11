package cn.upfinder.upfinder.Model.DB.Dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.upfinder.upfinder.Model.Bean.Contact;
import cn.upfinder.upfinder.Model.DB.DBHelper;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class ContactDao {

    private Context context;
    private Dao<Contact,Integer> contactDaoOpe;
    private DBHelper helper;

    public ContactDao(Context context){
        this.context = context;
        try {
            helper = DBHelper.getInstance(context);
            contactDaoOpe = helper.getDao(Contact.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    *添加一个联系人
    * */
    public void add(Contact contact){
        try {
            contactDaoOpe.create(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * 查询所有联系人数据
    * */
    public List<Contact> query() throws SQLException {
        List<Contact> contactList = contactDaoOpe.queryForAll();
        return contactList;
    }
}
