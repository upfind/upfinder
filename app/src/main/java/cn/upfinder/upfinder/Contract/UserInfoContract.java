package cn.upfinder.upfinder.Contract;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/8/3 0003.
 * 用户详情页的协议类
 */
public interface UserInfoContract {

    interface View extends BaseView<UserInfoContract.Presenter> {

        //初始化显示
        void initViewShow(User user);
    }

    interface Presenter extends BasePresenter {

    }
}
