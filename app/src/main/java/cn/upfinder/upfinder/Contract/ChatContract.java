package cn.upfinder.upfinder.Contract;

import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.Conversation;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/7/27 0027.
 * 聊天界面的协议类
 */
public interface ChatContract {

    interface View extends BaseView<Presenter> {

        //初始化聊天列表
        void initView(BmobIMConversation conversation);

        //返回
        void onBack();

        //显示聊天记录
        void showMessages(List<BmobIMMessage> messages);

        //跳转到用户详情
        void toUserInfoActivity();

        //停止刷新
        void hideRefreshing();

        //消息列表滚动到底部
        void scrollToBottom();

        //提示Toast
        void showToast(String msg);

        //添加单条消息
        void addMsg(BmobIMMessage message);

    }

    interface Presenter extends BasePresenter {

        //发送消息
        void sendTextMsg(String msgContent);

        /*查询消息方法
        * 首次加载时可以设置@message为null
        * 下拉刷新时默认将消息表的第一个message为刷新的起始时间点
        * 按照消息时间的降序排列*/
        void queryMessages(BmobIMMessage message);

        //添加页面消息监听器
        void addMsgListener();

    }
}
