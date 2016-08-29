package cn.upfinder.upfinder.Presenter;

import android.content.Context;

import cn.upfinder.upfinder.Contract.WelcomeContract;
import cn.upfinder.upfinder.Fragment.BaseView;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class WelcomePresenter implements WelcomeContract.Presenter {

    private Context context;
    private WelcomeContract.View welcomeView;

    public WelcomePresenter(Context context, WelcomeContract.View welcomeView) {
        this.context = context;
        this.welcomeView = welcomeView;
    }

    @Override
    public void start() {

    }
}
