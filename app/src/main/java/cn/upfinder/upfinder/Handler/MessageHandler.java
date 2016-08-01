package cn.upfinder.upfinder.Handler;

import android.content.Context;
import android.util.Log;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

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
    }

    /*
    * 每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用*/
    @Override
    public void onOfflineReceive(OfflineMessageEvent offlineMessageEvent) {
        super.onOfflineReceive(offlineMessageEvent);
        Log.d(TAG, "onOfflineReceive: " + offlineMessageEvent.getTotalNumber());
    }


}
