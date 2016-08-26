package cn.upfinder.upfinder.Presenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.listener.FindListener;
import cn.upfinder.upfinder.Activity.ChatActivity;
import cn.upfinder.upfinder.Contract.ContactContract;
import cn.upfinder.upfinder.Model.Bean.Contacts;
import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;
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
        obtainDBContactData();
    }

    @Override
    public void obtainContactData() {
        Log.d(TAG, "initContactData: ");
        //查询联系人数据
        UserModel.getInstance().queryFriends(new FindListener<Friend>() {
            @Override
            public void onSuccess(List<Friend> list) {
                Log.d(TAG, "onSuccess: 加载到好友" + list.size());
                contactView.showContacts(transData(list));
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
            List<Contacts> contactList = new ContactDao(context).query();
            contactView.showContacts(contactList);
            Log.d(TAG, "obtainDBContactData: 加载到本地缓存联系人数据" + contactList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toChatWithFriend(Contacts contacts) {

        //构造对方的
//        BmobIMUserInfo userInfo = new BmobIMUserInfo(friend.getFriendUser().getObjectId(), friend.getFriendUser().getUsername(), friend.getFriendUser().getAvatar());
        BmobIMUserInfo userInfo = new BmobIMUserInfo(contacts.getUserObjectId(), contacts.getNickName(), contacts.getUserAvatar());
        //启动一个会话
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(userInfo, false, null);
        PrivateConversation conversation = new PrivateConversation(c);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ChatActivity.INTENT_KEY_CONVERSATION, conversation);
        contactView.jumpToChatActivity(bundle);

    }

    @Override
    public List<Contacts> transData(List<Friend> friendList) {
        new ContactDao(context).delAllContacts();
        List<Contacts> contactList = new ArrayList<>();
        for (Friend friend : friendList) {
            Contacts contact = new Contacts();
            contact.setUserAvatar(friend.getFriendUser().getAvatar());
            contact.setNickName(friend.getFriendUser().getNick());
            contact.setUserObjectId(friend.getFriendUser().getObjectId());
            //更新本地缓存表
            new ContactDao(context).add(contact);
            contactList.add(contact);
        }
        return contactList;
    }


}
