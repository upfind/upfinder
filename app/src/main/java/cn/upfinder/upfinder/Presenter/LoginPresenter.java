package cn.upfinder.upfinder.Presenter;

import android.support.annotation.NonNull;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Contract.LoginContract;
import cn.upfinder.upfinder.Model.UserModel;

/**
 * Created by Upfinder on 2016/7/17 0017.
 */
public class LoginPresenter implements LoginContract.Presenter {

    private final User user;
    private final LoginContract.View loginView;

    public LoginPresenter(@NonNull User user, @NonNull LoginContract.View loginView) {
        this.user = user;
        this.loginView = loginView;
        this.loginView.setPresenter(this);
    }

    @Override
    public void login(String count, String pwd) {
        if (count.isEmpty()) {
            loginView.showCountErr();
            return;
        }
        if (pwd.isEmpty()) {
            loginView.showPwdErr();
            return;
        }
        loginView.changeBtnLoginStatus(true);
        loginView.showLoginProgress();
        User.loginByAccount(count, pwd, new LogInListener<User>() {
            @Override
            public void done(User bmobUser, BmobException e) {

                if (bmobUser != null) {
                    UserModel.getInstance().setLocalUser(bmobUser);
                    loginView.showLoginSuccess();
                } else {
                    loginView.showLoginErr();
                }

            }
        });

    }

    @Override
    public void start() {

    }
}
