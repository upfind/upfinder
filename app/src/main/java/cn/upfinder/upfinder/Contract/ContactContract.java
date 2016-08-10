package cn.upfinder.upfinder.Contract;

import java.util.List;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/8/5 0005.
 */
public interface ContactContract {

    interface View extends BaseView<ContactContract.Presenter> {

        //展示联系人数据
        void showContacts(List<Friend> friendList);

        //Toast展示提示
        void showToast(String msg);
    }

    interface Presenter extends BasePresenter {

        //加载联系人列表数据
        void initContactData();

        //删除单个联系人
        void delContact();

    }
}
