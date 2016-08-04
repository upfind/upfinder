package cn.upfinder.upfinder.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.upfinder.upfinder.Activity.ChatActivity;
import cn.upfinder.upfinder.Contract.UserInfoContract;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;
import cn.upfinder.upfinder.Model.Bean.User;

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

    }
}
