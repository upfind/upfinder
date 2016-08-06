package cn.upfinder.upfinder.Contract;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/8/5 0005.
 */
public interface FindContract {

    interface View extends BaseView<FindContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
