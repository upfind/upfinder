package cn.upfinder.upfinder.Contract;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        //显示输入账号错误
        void showRegisterCountErr(String errMsg);

        //显示输入密码错误
        void showRegisterPwdErr(String errMsg);

        //显示确认密码错误
        void showRegisterPwdConfirmErr();

        //注册成功
        void registerSuccess();

        //注册失败
        void registerFailed(String errMsg);

        //显示注册进度
        void showRegisterProgress();

        //隐藏注册进度
        void hideRegisterProgress();

        //修改注册按钮状态
        void changeRegisterBtnStatus(boolean isRegister);

        //已有账户
        void haveCount();
    }

    interface Presenter extends BasePresenter {

        void register(String count, String pwd, String pwdConfirm);
    }
}
