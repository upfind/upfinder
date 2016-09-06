package cn.upfinder.upfinder.Contract;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/7/21 0021.
 * 账户相关的页面的协议类
 */
public interface CountContract {

    interface View extends BaseView<CountContract.Presenter> {

        //显示账户信息
        void showCountInfo(User user);

        //编辑用户账户信息
        void toEditCountInfo();

        //展示用户头像大图
        void showCountUserLogo(User user);

        //退出登陆后跳转至登录界面
        void jumpToLoginActivity();
    }

    interface Presenter extends BasePresenter {

        //刷新账户信息数据
        void refreshCountInfo();

        //退出登录
        void logOut();

        //查看头像
        void jumpToPicture();
    }
}
