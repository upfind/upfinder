package cn.upfinder.upfinder.Presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.upfinder.upfinder.Contract.ChatContract;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Model.UserModel;

/**
 * Created by upfinder on 2016/7/27 0027.
 */
public class ChatPresenter implements ChatContract.Presenter {
    private final String TAG = ChatPresenter.class.getSimpleName();

    private ChatContract.View chatView;
    private BmobIMConversation conversation;
    private Context context;

    public ChatPresenter(Context context, ChatContract.View chatView, BmobIMConversation conversation) {
        this.context = context;
        this.chatView = chatView;
        this.conversation = conversation;

        chatView.setPresenter(this);
    }

    @Override
    public void start() {
        chatView.initView(conversation);
        //连接服务器
        User user = BmobUser.getCurrentUser(context, User.class);
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
        BmobIMUserInfo userInfo = new BmobIMUserInfo();
        userInfo.setAvatar(UserModel.getInstance().getLocalUser().getAvatar());
        userInfo.setName(UserModel.getInstance().getLocalUser().getNick());
        msg.setBmobIMUserInfo(userInfo);
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
    public void sendLocalImgMsg(String imgPath) {
        Log.d(TAG, "sendLocalImgMsg: " + imgPath);
        if (TextUtils.isEmpty(imgPath)) {
            chatView.showToast("图片选择为空");
            return;
        }

        BmobIMImageMessage imageMessage = new BmobIMImageMessage(imgPath);
//        BmobIMImageMessage imageMessage = new BmobIMImageMessage();
//        imageMessage.setRemoteUrl("http://img.lakalaec.com/ad/57ab6dc2-43f2-4087-81e2-b5ab5681642d.jpg");
        conversation.sendMessage(imageMessage, listener);
    }


    /**
     * 消息发送监听器
     */
    public MessageSendListener listener = new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);
            //文件类型的消息才有进度值
            Log.i(TAG, "onProgress: " + value);
        }

        @Override
        public void postProgress(int i) {
            super.postProgress(i);
        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);


        }

        @Override
        public void done(BmobIMMessage msg, BmobException e) {
            chatView.addMsg(msg);
            chatView.scrollToBottom();
            if (e != null) {
                chatView.showToast(e.getMessage());
            }
        }
    };


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
                for (MessageEvent event : list) {
                    BmobIMMessage msg = event.getMessage();
                    //是当前会话消息 并且消息不为暂态消息
                    if (conversation != null && event != null && conversation.getConversationId().equals(event.getConversation().getConversationId()) && !msg.isTransient()) {
                        chatView.addMsg(msg);
                        chatView.scrollToBottom();
                    } else {
                        Log.e(TAG, "onMessageReceive: 不是当前聊天对象消息");
                    }
                }

            }
        });
    }

    @Override
    public void removeMsgListener() {
        BmobIM.getInstance().removeMessageListHandler(new MessageListHandler() {
            @Override
            public void onMessageReceive(List<MessageEvent> list) {

                Log.d(TAG, "onMessageReceive: 移除页面新消息监听");
            }
        });
    }

    @Override
    public void readAllMsg() {
        if (conversation != null) {
            conversation.updateLocalCache();
        }
    }
}
