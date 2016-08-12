package cn.upfinder.upfinder.Contract;

import java.util.List;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.NewFriend;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public interface NewFriendContract {

    interface View extends BaseView<NewFriendContract.Presenter> {

        //显示新好友数据
        void showAllNewFriend(List<NewFriend> newFriendList);

        //显示Toast
        void showToast(String msg);
    }

    interface Presenter extends BasePresenter {

        //查询本地缓存的所有新好友信息
        void queryAllNewFriend();

    }
}
