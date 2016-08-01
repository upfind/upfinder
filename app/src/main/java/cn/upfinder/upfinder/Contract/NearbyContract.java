package cn.upfinder.upfinder.Contract;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/7/21 0021.
 * 附近的页面的协议类
 */
public interface NearbyContract {

    interface View extends BaseView<NearbyContract.Precenter> {

    }

    interface Precenter extends BasePresenter {

    }
}
