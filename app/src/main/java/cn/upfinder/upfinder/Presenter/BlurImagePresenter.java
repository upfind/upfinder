package cn.upfinder.upfinder.Presenter;

import android.content.Context;

import cn.upfinder.upfinder.Contract.BlurImageContract;

/**
 * Created by upfinder on 2016/8/24 0024.
 */
public class BlurImagePresenter implements BlurImageContract.Presenter {

    private Context context;
    private BlurImageContract.View blurImageView;

    public BlurImagePresenter(Context context, BlurImageContract.View blurImageView) {
        this.context = context;
        this.blurImageView = blurImageView;
        this.blurImageView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
