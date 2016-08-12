package cn.upfinder.upfinder.Presenter;

import android.content.Context;

import cn.upfinder.upfinder.Contract.NewFriendContract;

/**
 * Created by upfinder on 2016/8/12 0012.
 */
public class NewFriendPresenter implements NewFriendContract.Presenter {

    private Context context;
    private NewFriendContract.View newFriendView;

    public NewFriendPresenter(Context context, NewFriendContract.View newFriendView) {
        this.context = context;
        this.newFriendView = newFriendView;
        this.newFriendView.setPresenter(this);

    }

    @Override
    public void start() {

    }
}
