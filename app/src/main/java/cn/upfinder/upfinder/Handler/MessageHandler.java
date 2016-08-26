package cn.upfinder.upfinder.Handler;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;
import cn.upfinder.upfinder.Activity.HomeActivity;
import cn.upfinder.upfinder.Config;
import cn.upfinder.upfinder.Model.Bean.NewFriend;
import cn.upfinder.upfinder.Model.DB.Dao.NewFriendDao;
import cn.upfinder.upfinder.Model.UpdateCacheListener;
import cn.upfinder.upfinder.Model.UserModel;

/**
 * Created by Upfinder on 2016/7/18 0018.
 * 消息接收器
 */
public class MessageHandler extends BmobIMMessageHandler {
    private final String TAG = MessageHandler.class.getSimpleName();

    private Context context;

    public MessageHandler(Context context) {
        this.context = context;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.d(TAG, "handleMessage: 成功");
                    break;
            }
            return false;
        }
    });


    /*
    * 当接收到服务器发来的消息时，调用次方法*/
    @Override
    public void onMessageReceive(MessageEvent messageEvent) {
        super.onMessageReceive(messageEvent);
        Log.d(TAG, "onMessageReceive: " + messageEvent.getMessage());
        handleMsg(messageEvent);
    }

    /*
    * 每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用*/
    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        super.onOfflineReceive(offlineMessageEvent);
        Log.d(TAG, "onOfflineReceive: " + offlineMessageEvent.getTotalNumber());
    }


    /*
    * 当接收到消息时处理消息
    * */
    private void handleMsg(final MessageEvent messageEvent) {
        //检测用户信息是否需要更新
        UserModel.getInstance().updateUserInfo(messageEvent, new UpdateCacheListener() {
            @Override
            public void done(BmobException e) {
                BmobIMMessage msg = messageEvent.getMessage();
                BmobIMUserInfo userInfo = messageEvent.getFromUserInfo();
                int type = BmobIMMessageType.getMessageTypeValue(msg.getMsgType());
                Log.d(TAG, "done: " + type);
                if (BmobIMMessageType.getMessageTypeValue(msg.getMsgType()) == 0) {//用户自定义的消息类型，其类型值均为0
                    Log.d(TAG, "done: 处理自定义类型消息");
                    if (msg.getMsgType().equals(Config.MSG_TYPE_ADD_NEW_FRIEND)) { //收到添加好友请求
                        handleAddFriendMsg(userInfo);
                    } else if (msg.getMsgType().equals(Config.MSG_TYPE_AGREE_ADD_FRIEND)) { //收到同意添加回复
                        handleAgreeAddMsg(msg);
                    }
                } else { // SDK内部支持的消息类型
                    if (BmobNotificationManager.getInstance(context).isShowNotification()) {//如果需要显示通知栏，SDK提供以下两种显示方式：
                        Intent pendingIntent = new Intent(context, HomeActivity.class);
                        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //1：多个用户的多条信息合并成一条通知: 有XX联系人发来XX条消息
                        BmobNotificationManager.getInstance(context).showNotification(messageEvent, pendingIntent);
                        //2：自定义通知消息 ：始终只有一条通知，新消息覆盖旧消息
                        //                        BmobIMUserInfo info =event.getFromUserInfo();
//                        //这里可以是应用图标，也可以将聊天头像转成bitmap
//                        Bitmap largetIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
//                        BmobNotificationManager.getInstance(context).showNotification(largetIcon,
//                                info.getName(),msg.getContent(),"您有一条新消息",pendingIntent);
                    } else { //直接发送消息事件
                        Log.d(TAG, "done: 发送消息事件 msgEvent");
                    }
                }
            }
        });
    }

    //处理添加好友请求消息
    private void handleAddFriendMsg(BmobIMUserInfo userInfo) {
        /*1.处理信息，获得请求方的账户信息upfinder
        * 2.记录状态，添加至关系表
        * 3.将请求方账户数据信息缓存至本地
        * */
        String fromId = userInfo.getUserId();
        String userAvatar = userInfo.getAvatar();
        String userName = userInfo.getName();
        NewFriend newFriend = new NewFriend(fromId, NewFriend.ASK_ADD_ME);
        newFriend.setUserAvatar(userAvatar);
        newFriend.setUserName(userName);
        new NewFriendDao(context).add(newFriend);
    }

    //处理同意好友添加请求消息
    private void handleAgreeAddMsg(BmobIMMessage message) {
        /*1.对方同意添加后，在关系表中更改与对方关系为好友状态
        * 2.并改变本地关系表中状态
        * */
    }

}
