package cn.upfinder.upfinder.Presenter;

import android.content.Context;

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
    private Context context;

    public RegisterPresenter(Context context, RegisterContract.View registerView) {
        this.context = context;
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


        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                registerView.registerSuccess();
                registerView.changeRegisterBtnStatus(false);
                registerView.hideRegisterProgress();
            }

            @Override
            public void onFailure(int i, String s) {
                registerView.registerFailed(s);
                registerView.changeRegisterBtnStatus(false);
                registerView.hideRegisterProgress();
            }


        });

    }

    @Override
    public void start() {

    }
}
