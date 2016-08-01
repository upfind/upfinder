package cn.upfinder.upfinder.Presenter;

import android.support.annotation.NonNull;

import cn.upfinder.upfinder.Contract.NearbyContract;

/**
 * Created by upfinder on 2016/7/21 0021.
 * 附近的页面的控制类
 */
public class NearbyPresenter implements NearbyContract.Precenter {


    private final NearbyContract.View nearbyView;

    public NearbyPresenter(@NonNull NearbyContract.View nearbyView) {
        this.nearbyView = nearbyView;
        this.nearbyView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
