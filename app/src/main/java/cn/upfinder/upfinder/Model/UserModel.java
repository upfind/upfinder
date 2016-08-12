package cn.upfinder.upfinder.Model;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.Model.Bean.User;

/**
 * Created by Upfinder on 2016/7/21 0021.
 * 用户管理类 本地缓存账户信息
 */
public class UserModel extends BaseModel {
    private final String TAG = UserModel.class.getSimpleName();

    private static UserModel ourInstance;

    private static User localUser;

    public static UserModel getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserModel();
        }
        return ourInstance;
    }

    public User getLocalUser() {

        if (localUser == null) {
            localUser = BmobUser.getCurrentUser(getContext(), User.class);
        }
        return localUser;
    }

    /*
    * 退出登录*/
    public void logOut() {
        BmobUser.logOut(getContext());
        //断开连接
        BmobIM.getInstance().disConnect();

    }

    public void setLocalUser(User localUser) {
        UserModel.localUser = localUser;
    }

    //同步本地User数据至服务器
    public void syncToServer(Context context, UpdateListener updateListener) {
        getLocalUser().update(context, updateListener);
    }


    /*
    * 查询用户信息*/
    public void queryUserInfo(String objectId, final QueryUserListener listener) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", objectId);
        query.findObjects(getContext(), new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                if (list != null && list.size() > 0) {
                    listener.internalDone(list.get(0), null);
                } else {
                    listener.internalDone(new BmobException(000, "查无此人"));
                }
            }

            @Override
            public void onError(int i, String s) {
                listener.internalDone(new BmobException(i, s));
            }
        });
    }


    /*
    * 更新用户资料和会话资料
    * */
    public void updateUserInfo(final MessageEvent event, final UpdateCacheListener listener) {

        final BmobIMConversation conversation = event.getConversation();
        final BmobIMUserInfo userInfo = event.getFromUserInfo();
        final BmobIMMessage msg = event.getMessage();
        String userName = userInfo.getName();
        String title = conversation.getConversationTitle();
        Log.d(TAG, "updateUserInfo: " + userName + "//" + title);
        if (!userName.equals(title)) {
            UserModel.getInstance().queryUserInfo(userInfo.getUserId(), new QueryUserListener() {
                @Override
                public void done(User s, BmobException e) {
                    if (e == null) {
                        String name = s.getUsername();
                        String avatar = s.getAvatar();
                        Log.d(TAG, "done: name:" + name);
                        Log.d(TAG, "done: avatar:" + avatar);
                        conversation.setConversationIcon(avatar);
                        conversation.setConversationTitle(name);
                        userInfo.setName(name);
                        userInfo.setAvatar(avatar);

                        //更新用户资料
                        BmobIM.getInstance().updateUserInfo(userInfo);

                        //跟新会话资料 ，如果消息是暂时状态，则不更新会话资料
                        if (msg.isTransient()) {
                            BmobIM.getInstance().updateConversation(conversation);
                        }
                    } else {
                        Log.e(TAG, "done: " + e.getMessage());
                    }
                    listener.done(null);
                }
            });
        } else {
            listener.internalDone(null);
        }
    }

    /*
    * 查询好友 从服务器
    * */
    public void queryFriends(final FindListener<Friend> listener) {
        BmobQuery<Friend> query = new BmobQuery<>();
        User user = BmobUser.getCurrentUser(getContext(), User.class);
        query.addWhereEqualTo("user", user);
        query.include("friendUser");
        query.order("_updatedAt");
        query.findObjects(getContext(), new FindListener<Friend>() {
            @Override
            public void onSuccess(List<Friend> list) {
                if (list != null && list.size() > 0) {
                    listener.onSuccess(list);
                } else {
                    listener.onError(0, "暂无联系人");
                }
            }

            @Override
            public void onError(int i, String s) {

                listener.onError(i, s);
            }
        });
    }

    /**
     * 同意添加好友：1、发送同意添加的请求，2、添加对方到自己的好友列表中
     */
    public void agreeAddFriend(User friend,SaveListener listener){
        Friend f = new Friend();
        User user =BmobUser.getCurrentUser(getContext(), User.class);
        f.setUser(user);
        f.setFriendUser(friend);
        f.save(getContext(),listener);
    }

    /**
     * 删除好友
     * @param f
     * @param listener
     */
    public void deleteFriend(Friend f,DeleteListener listener){
        Friend friend =new Friend();
        friend.delete(getContext(),f.getObjectId(),listener);
    }
}
