package cn.upfinder.upfinder.Presenter;

import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.upfinder.upfinder.Contract.ChatContract;
import cn.upfinder.upfinder.Model.Bean.User;

/**
 * Created by upfinder on 2016/7/27 0027.
 */
public class ChatPresenter implements ChatContract.Presenter {
    private final String TAG = ChatPresenter.class.getSimpleName();

    private ChatContract.View chatView;
    private BmobIMConversation conversation;

    public ChatPresenter(ChatContract.View chatView, BmobIMConversation conversation) {
        this.chatView = chatView;
        this.conversation = conversation;
        chatView.setPresenter(this);
    }

    @Override
    public void start() {
        chatView.initView(conversation);
        //连接服务器
        User user = BmobUser.getCurrentUser(User.class);
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "done: 连接服务器成功");
                } else {
                    Log.e(TAG, "done: 连接服务器失败");
                    chatView.showToast(e.getMessage());
                }
            }
        });
    }

    @Override
    public void sendTextMsg(String msgContent) {
        Log.d(TAG, "sendTextMsg: " + msgContent);
        if (TextUtils.isEmpty(msgContent)) {
            chatView.showToast("请输入内容");
            return;
        }

        BmobIMTextMessage msg = new BmobIMTextMessage();
        msg.setContent(msgContent);
        conversation.sendMessage(msg, new MessageSendListener() {

            @Override
            public void onProgress(int i) {
                super.onProgress(i);
                Log.d(TAG, "onProgress: " + i);
            }

            @Override
            public void onStart(BmobIMMessage bmobIMMessage) {
                super.onStart(bmobIMMessage);

            }

            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {

                if (e == null) {
                    chatView.addMsg(bmobIMMessage);
                    chatView.scrollToBottom();
                } else {
                    chatView.showToast(e.getMessage());
                    Log.e(TAG, "done: 发送消息失败" + e.getErrorCode() + e.getMessage());
                }

            }
        });
    }


    @Override
    public void queryMessages(BmobIMMessage message) {

        conversation.queryMessages(message, 10, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "done: 查询成功" + list.size());
                    if (list != null && list.size() > 0) {
                        chatView.showMessages(list);
                    }
                } else {
                    Log.e(TAG, "done: 查询失败" + e.getMessage());
                }
                chatView.hideRefreshing();
            }
        });
    }

    @Override
    public void addMsgListener() {
        BmobIM.getInstance().addMessageListHandler(new MessageListHandler() {
            @Override
            public void onMessageReceive(List<MessageEvent> list) {

                Log.d(TAG, "onMessageReceive: 聊天界面收到消息" + list.size());
            }
        });
    }
}
