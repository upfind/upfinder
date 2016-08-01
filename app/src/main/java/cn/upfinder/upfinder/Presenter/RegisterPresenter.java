package cn.upfinder.upfinder.Presenter;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Contract.RegisterContract;

/**
 * Created by Upfinder on 2016/7/17 0017.
 */
public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View registerView;

    public RegisterPresenter(RegisterContract.View registerView) {
        this.registerView = registerView;

        this.registerView.setPresenter(this);
    }


    @Override
    public void register(String count, String pwd, String pwdConfirm) {

        if (count.isEmpty()) {
            registerView.showRegisterCountErr("请输入账号");
            return;
        }
        if (pwd.isEmpty()) {
            registerView.showRegisterPwdErr("请输入密码");
            return;
        }
        if (!pwd.equals(pwdConfirm)) {
            registerView.showRegisterPwdConfirmErr();
            return;
        }

        registerView.changeRegisterBtnStatus(true);
        registerView.showRegisterProgress();
        User user = new User();
        user.setUsername(count);
        user.setPassword(pwd);

        user.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    registerView.registerSuccess();
                    registerView.changeRegisterBtnStatus(false);
                    registerView.hideRegisterProgress();
                } else {
                    registerView.registerFailed(e.getMessage());
                    registerView.changeRegisterBtnStatus(false);
                    registerView.hideRegisterProgress();
                }

            }
        });

    }

    @Override
    public void start() {

    }
}
