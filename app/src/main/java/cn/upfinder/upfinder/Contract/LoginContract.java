package cn.upfinder.upfinder.Contract;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by Upfinder on 2016/7/17 0017.
 * 登录协调接口
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        //显示登录失败
        void showLoginErr();

        //显示输入账号错误
        void showCountErr();

        //显示输入密码错误
        void showPwdErr();

        //显示登录成功
        void showLoginSuccess();

        //显示登录进度
        void showLoginProgress();

        //隐藏登录进度
        void hideLoginProgress();

        //修改登录按钮状态
        void changeBtnLoginStatus(boolean isLogining);

        //前往注册
        void goToRegister();
    }

    interface Presenter extends BasePresenter {
        //登录
        void login(String count, String pwd);

    }
}
