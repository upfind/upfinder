package cn.upfinder.upfinder.Contract;

import android.os.Bundle;

import java.util.List;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.Contacts;
import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/8/5 0005.
 */
public interface ContactContract {

    interface View extends BaseView<ContactContract.Presenter> {

        //展示联系人数据
        void showContacts(List<Contacts> contactsList);

        //Toast展示提示
        void showToast(String msg);

        //跳转到聊天页面
        void jumpToChatActivity(Bundle bundle);
    }

    interface Presenter extends BasePresenter {

        //加载联系人列表数据
        void obtainContactData();

        //删除单个联系人
        void delContact();

        //加载数据库缓存数据
        void obtainDBContactData();

        //发送消息给好友
        void toChatWithFriend(Contacts contacts);

        //加载数据 1.获取当前数据库缓存数据 2.若数据库中没有缓存则加载服务器并同时缓存到数据库 3.当下拉刷新时拉取服务器


        //将加载的网络数据Friend数据，转换成Contact类数据
        List<Contacts> transData(List<Friend> friendList);

    }
}
