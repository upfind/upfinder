package cn.upfinder.upfinder.Contract;

import android.app.Activity;
import android.os.Bundle;

import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/8/3 0003.
 * 用户详情页的协议类
 */
public interface UserInfoContract {

    interface View extends BaseView<UserInfoContract.Presenter> {

        //初始化显示
        void initViewShow(User user);

        //跳转到聊天页面
        void jumpToChatActivity(Bundle bundle);
    }

    interface Presenter extends BasePresenter {

        //发送消息
        void sendMsg();

        //发送好友请求
        void sendAddRequest();
    }
}
