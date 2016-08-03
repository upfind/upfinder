package cn.upfinder.upfinder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;
import cn.bmob.v3.BmobUser;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;

/**
 * Created by upfinder on 2016/7/28 0028.
 * 聊天界面展示messages的Adapter
 * 1.定义不同ViewHolder,基于BaseViewHolder
 * 2.根据不同的消息类型（以及收发）返回不同的ViewHolder
 */
public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = ChatAdapter.class.getSimpleName();

    //文本
    private final int TYPE_RECEIVER_TXT = 0;
    private final int TYPE_SEND_TXT = 1;
    //图片
    private final int TYPE_SEND_IMAGE = 2;
    private final int TYPE_RECEIVER_IMAGE = 3;
    //位置
    private final int TYPE_SEND_LOCATION = 4;
    private final int TYPE_RECEIVER_LOCATION = 5;
    //语音
    private final int TYPE_SEND_VOICE = 6;
    private final int TYPE_RECEIVER_VOICE = 7;
    //视频
    private final int TYPE_SEND_VIDEO = 8;
    private final int TYPE_RECEIVER_VIDEO = 9;

    //同意添加好友成功后的样式
    private final int TYPE_AGREE = 10;

    private List<BmobIMMessage> messageList = new ArrayList<>();

    private String currentUid = "";

    private BmobIMConversation conversation;
    private Context context;

    private OnRecyclerViewListener onRecyclerViewListener;

    /**
     * 显示时间间隔:10分钟
     */
    private final long TIME_INTERVAL = 10 * 60 * 1000;


    public ChatAdapter(Context context, BmobIMConversation conversation) {
        try {
            currentUid = BmobUser.getCurrentUser(context).getObjectId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.context = context;
        this.conversation = conversation;
    }

    public int findPosition(BmobIMMessage message) {
        int index = this.getCount();
        int position = -1;
        while (index-- > 0) {
            if (message.equals(this.getItem(index))) {
                position = index;
                break;
            }
        }
        return position;
    }

    public int findPosition(long id) {
        int index = this.getCount();
        int position = -1;
        while (index-- > 0) {
            if (this.getItemId(index) == id) {
                position = index;
                break;
            }
        }
        return position;
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public int getCount() {
        return this.messageList == null ? 0 : this.messageList.size();
    }

    public void addMessages(List<BmobIMMessage> messages) {
        Log.d(TAG, "addMessages: " + messages.size());
//        messageList.clear();
//        messageList.addAll(messages);
        messageList.addAll(0, messages);
        notifyDataSetChanged();
    }

    public void addMessage(BmobIMMessage message) {
        messageList.addAll(Arrays.asList(message));
        notifyDataSetChanged();
    }

    /**
     * 获取消息
     */
    public BmobIMMessage getItem(int position) {
        return this.messageList == null ? null : (position >= this.messageList.size() ? null : this.messageList.get(position));
    }

    /**
     * 移除消息
     */
    public void remove(int position) {
        messageList.remove(position);
        notifyDataSetChanged();
    }

    public BmobIMMessage getFirstMessage() {
        if (null != messageList && messageList.size() > 0) {
            return messageList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SEND_TXT) {
            return new SendTextHolder(parent.getContext(), parent, conversation, onRecyclerViewListener);
        } else if (viewType == TYPE_RECEIVER_TXT) {
            return new ReceiveTextHolder(parent.getContext(), parent, onRecyclerViewListener);
        } else if (viewType == TYPE_RECEIVER_IMAGE) {
            return new ReceiveImageHolder(parent.getContext(), parent, onRecyclerViewListener);
        } else if (viewType == TYPE_SEND_IMAGE) {
            return new SendImageHolder(parent.getContext(), parent, onRecyclerViewListener);
        } else {//开发者自定义的其他类型，可自行处理
            return new SendTextHolder(parent.getContext(), parent, conversation, onRecyclerViewListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder) holder).bindData(messageList.get(position));
        if (holder instanceof SendTextHolder) {
            ((SendTextHolder) holder).showTime(shouldShowTime(position));
        } else if (holder instanceof ReceiveTextHolder) {
            ((ReceiveTextHolder) holder).showTime(shouldShowTime(position));
        } else if (holder instanceof ReceiveImageHolder) {
            ((ReceiveImageHolder) holder).showTime(shouldShowTime(position));
        } else if (holder instanceof SendImageHolder) {
            ((SendImageHolder) holder).showTime(shouldShowTime(position));
        }

    }

    @Override
    public int getItemViewType(int position) {
        BmobIMMessage message = messageList.get(position);
        if (message.getMsgType().equals(BmobIMMessageType.IMAGE.getType())) {
            return message.getFromId().equals(currentUid) ? TYPE_SEND_IMAGE : TYPE_RECEIVER_IMAGE;
        } else if (message.getMsgType().equals(BmobIMMessageType.LOCATION.getType())) {
            return message.getFromId().equals(currentUid) ? TYPE_SEND_LOCATION : TYPE_RECEIVER_LOCATION;
        } else if (message.getMsgType().equals(BmobIMMessageType.VOICE.getType())) {
            return message.getFromId().equals(currentUid) ? TYPE_SEND_VOICE : TYPE_RECEIVER_VOICE;
        } else if (message.getMsgType().equals(BmobIMMessageType.TEXT.getType())) {
            return message.getFromId().equals(currentUid) ? TYPE_SEND_TXT : TYPE_RECEIVER_TXT;
        } else if (message.getMsgType().equals(BmobIMMessageType.VIDEO.getType())) {
            return message.getFromId().equals(currentUid) ? TYPE_SEND_VIDEO : TYPE_RECEIVER_VIDEO;
        } else if (message.getMsgType().equals("agree")) {//显示欢迎
            return TYPE_AGREE;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }
        long lastTime = messageList.get(position - 1).getCreateTime();
        long curTime = messageList.get(position).getCreateTime();
        return curTime - lastTime > TIME_INTERVAL;
    }
}
