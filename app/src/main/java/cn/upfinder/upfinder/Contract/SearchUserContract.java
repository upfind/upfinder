package cn.upfinder.upfinder.Contract;

import java.util.ArrayList;
import java.util.List;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/7/23 0023.
 * 搜索用户协议接口
 */
public interface SearchUserContract {

    interface View extends BaseView<SearchUserContract.Presenter> {

        //初始化布局
        void initView();

        //显示搜索结果
        void showSearchResult(List<User> userList);

        //显示搜索进度
        void showSearchProgress();

        //显示错误消息
        void showErrMsg(String errMsg);


    }

    interface Presenter extends BasePresenter {

        //搜索用户
        void searchUser(String userName);
    }
}
