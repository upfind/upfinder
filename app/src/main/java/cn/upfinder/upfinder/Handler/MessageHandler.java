package cn.upfinder.upfinder.Handler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.exception.BmobException;
import cn.upfinder.upfinder.Activity.HomeActivity;
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
                if (BmobIMMessageType.getMessageTypeValue(msg.getMsgType()) == 0) {//用户自定义的消息类型，其类型值均为0
                    Log.d(TAG, "done: 处理自定义类型消息");
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

}
