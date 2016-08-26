package cn.upfinder.upfinder.Model.DB.Dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import cn.upfinder.upfinder.Model.Bean.Contacts;
import cn.upfinder.upfinder.Model.DB.DBHelper;

/**
 * Created by upfinder on 2016/8/11 0011.
 */
public class ContactDao {

    private Context context;
    private Dao<Contacts, Integer> contactDaoOpe;
    private DBHelper helper;

    public ContactDao(Context context) {
        this.context = context;
        try {
            helper = DBHelper.getInstance(context);
            contactDaoOpe = helper.getDao(Contacts.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    *添加一个联系人
    * */
    public void add(Contacts contact) {
        try {
            contactDaoOpe.create(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * 更新一个联系人
    * */
    public void upContacts(Contacts contacts) {
        try {
            contactDaoOpe.update(contacts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * 查询所有联系人数据
    * */
    public List<Contacts> query() throws SQLException {
        List<Contacts> contactList = contactDaoOpe.queryForAll();
        return contactList;
    }

    /*
    * 删除本地缓存的所有联系人
    * */
    public void delAllContacts() {
        for (Contacts contacts : contactDaoOpe) {
            try {
                contactDaoOpe.delete(contacts);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
