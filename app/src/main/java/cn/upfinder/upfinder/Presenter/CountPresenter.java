package cn.upfinder.upfinder.Presenter;

import cn.upfinder.upfinder.Contract.CountContract;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Model.UserModel;

/**
 * Created by upfinder on 2016/7/22 0022.
 * 账户相关页面管理类
 */
public class CountPresenter implements CountContract.Precenter {


    private CountContract.View countView;

    public CountPresenter(CountContract.View countView) {
        this.countView = countView;
        countView.setPresenter(this);
    }

    @Override
    public void start() {

        User user = UserModel.getInstance().getLocalUser();
        countView.showCountInfo(user);
    }

    @Override
    public void refreshCountInfo() {

    }

    @Override
    public void logOut() {
        UserModel.getInstance().logOut();
        countView.jumpToLoginActivity();
    }
}
