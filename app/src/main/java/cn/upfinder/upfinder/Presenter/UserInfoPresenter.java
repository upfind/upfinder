package cn.upfinder.upfinder.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMExtraMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.upfinder.upfinder.Activity.ChatActivity;
import cn.upfinder.upfinder.Config;
import cn.upfinder.upfinder.Contract.UserInfoContract;
import cn.upfinder.upfinder.Model.Bean.AddFriendMessage;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Model.UserModel;
import cn.upfinder.upfinder.Utils.ToastUtil;

/**
 * Created by upfidner on 2016/8/3 0003.
 * 用户详情页控制类
 */
public class UserInfoPresenter implements UserInfoContract.Presenter {
    private final String TAG = UserInfoPresenter.class.getSimpleName();

    private Context context;
    private User user;
    private UserInfoContract.View userInfoView;

    public UserInfoPresenter(Context context, User user, UserInfoContract.View userInfoView) {
        this.context = context;
        this.user = user;
        this.userInfoView = userInfoView;
        this.userInfoView.setPresenter(this);
    }


    @Override
    public void start() {

        userInfoView.initViewShow(user);
    }

    @Override
    public void sendMsg() {

        //构造聊天方的用户信息:传入用户id、用户昵称和用户头像三个参数
        BmobIMUserInfo userInfo = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar());
        //启动一个会话，设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(userInfo, false, null);
        PrivateConversation conversation = new PrivateConversation(c);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ChatActivity.INTENT_KEY_CONVERSATION, conversation);
        userInfoView.jumpToChatActivity(bundle);
    }

    @Override
    public void sendAddRequest() {
        //启动一个会话，如果isTransient设置为true,则不会创建在本地会话表中创建记录，
        //设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
//        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar());
//        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
//        //这个obtain方法才是真正创建一个管理消息发送的会话
//        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
//        AddFriendMessage msg = new AddFriendMessage();
//        User currentUser = BmobUser.getCurrentUser(context, User.class);
//        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方的一个留言信息
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", currentUser.getUsername());//发送者姓名，这里只是举个例子，其实可以不需要传发送者的信息过去
//        map.put("avatar", currentUser.getAvatar());//发送者的头像
//        map.put("uid", currentUser.getObjectId());//发送者的uid
//        msg.setExtraMap(map);
//        conversation.sendMessage(msg, new MessageSendListener() {
//            @Override
//            public void done(BmobIMMessage msg, BmobException e) {
//                if (e == null) {//发送成功
//                    userInfoView.showToast("好友请求发送成功，等待验证");
//                } else {//发送失败
//                    userInfoView.showToast("发送失败:" + e.getMessage());
//                }
//            }
//        });

        BmobIMExtraMessage message = new BmobIMExtraMessage();
        message.setMsgType(Config.MSG_TYPE_ADD_NEW_FRIEND);
        message.setContent("很高兴认识你，能加个好友么？");
        //消息的附加信息
        Map<String, Object> map = new HashMap<>();
        map.put("name", UserModel.getInstance().getLocalUser().getUsername());//发送者姓名，这里只是举个例子，其实可以不需要传发送者的信息过去
        map.put("avatar", UserModel.getInstance().getLocalUser().getAvatar());//发送者的头像
        map.put("uid", UserModel.getInstance().getLocalUser().getObjectId());//发送者的uid
        message.setExtraMap(map);

        BmobIMUserInfo info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar());
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //这个obtain方法才是真正创建一个管理消息发送的会话
        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
        conversation.sendMessage(message, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {

                if (e == null) {

                    userInfoView.showToast("添加请求已成功发送！");
                } else {
                    userInfoView.showToast("添加请求发送失败" + e.getMessage());
                }
            }
        });
    }
}
