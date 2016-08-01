package cn.upfinder.upfinder.Contract;

import android.provider.Telephony;
import android.widget.ListView;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.Conversation;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/7/21 0021.
 * 会话页面的协议类
 */
public interface MsgContract {

    interface View extends BaseView<MsgContract.Precenter> {

        //显示加载进度
        void showProgress();

        //隐藏加载进度
        void hideProgress();

        //若没有会话数据，显示添加会话布局
        void showAddConversation();

        //展示会话数据
        void showConversations(List<Conversation> conversationList);
    }

    interface Precenter extends BasePresenter {

        //加载会话列表数据
        void initConversationData();

        //进行添加会话跳转
        void toAddConversation();

        //删除单个会话
        void delConversation();

    }
}
