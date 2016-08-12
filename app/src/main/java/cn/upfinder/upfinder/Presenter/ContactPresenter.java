package cn.upfinder.upfinder.Presenter;

import android.content.Context;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.FindListener;
import cn.upfinder.upfinder.Contract.ContactContract;
import cn.upfinder.upfinder.Model.Bean.Contact;
import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.Model.DB.DBHelper;
import cn.upfinder.upfinder.Model.DB.Dao.ContactDao;
import cn.upfinder.upfinder.Model.UserModel;

/**
 * Created by upfinder on 2016/8/5 0005.
 */
public class ContactPresenter implements ContactContract.Presenter {
    private final String TAG = ContactPresenter.class.getSimpleName();

    private Context context;
    private ContactContract.View contactView;

    public ContactPresenter(Context context, ContactContract.View contactView) {
        this.context = context;
        this.contactView = contactView;
        this.contactView.setPresenter(this);
    }

    @Override
    public void start() {
        obtainContactData();
    }

    @Override
    public void obtainContactData() {
        Log.d(TAG, "initContactData: ");
        //查询联系人数据
        UserModel.getInstance().queryFriends(new FindListener<Friend>() {
            @Override
            public void onSuccess(List<Friend> list) {
                Log.d(TAG, "onSuccess: 加载到好友" + list.size());
                contactView.showContacts(list);
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "onError: 错误码" + i + ":" + s);
                contactView.showToast(s);
            }
        });

    }

    @Override
    public void delContact() {

    }

    @Override
    public void obtainDBContactData() {
        try {
            List<Contact> contactList = new ContactDao(context).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contact> transData(List<Friend> friendList) {
        List<Contact> contactList = new ArrayList<>();
        for (Friend friend : friendList) {
            Contact contact = new Contact();
            contact.setUserAvatar(friend.getFriendUser().getAvatar());
            contact.setNickName(friend.getFriendUser().getNick());
            contact.setUserObjectId(friend.getFriendUser().getObjectId());
            contactList.add(contact);
        }
        return contactList;
    }


}
