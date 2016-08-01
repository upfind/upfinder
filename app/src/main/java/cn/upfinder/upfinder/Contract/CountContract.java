package cn.upfinder.upfinder.Contract;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/7/21 0021.
 * 账户相关的页面的协议类
 */
public interface CountContract {

    interface View extends BaseView<CountContract.Precenter> {

        //显示账户信息
        void showCountInfo(User user);

        //编辑用户账户信息
        void toEditCountInfo();

        //展示用户头像大图
        void showCountUserLogo();
    }

    interface Precenter extends BasePresenter {

        //刷新账户信息数据
        void refreshCountInfo();
    }
}
