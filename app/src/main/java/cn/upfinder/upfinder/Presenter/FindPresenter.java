package cn.upfinder.upfinder.Presenter;

import android.content.Context;

import cn.upfinder.upfinder.Contract.FindContract;

/**
 * Created by upfinder on 2016/8/5 0005.
 */
public class FindPresenter implements FindContract.Presenter {
    private final String TAG = FindPresenter.class.getSimpleName();

    private Context context;
    private FindContract.View findView;

    public FindPresenter(Context context, FindContract.View findView) {
        this.context = context;
        this.findView = findView;
        this.findView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
