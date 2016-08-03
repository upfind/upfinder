package cn.upfinder.upfinder.Presenter;

import android.content.Context;

import cn.upfinder.upfinder.Contract.UserInfoContract;
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

    }
}
