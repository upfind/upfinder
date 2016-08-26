package cn.upfinder.upfinder.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.upfinder.upfinder.Contract.MsgContract;
import cn.upfinder.upfinder.Model.Bean.Conversation;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;

/**
 * Created by upfinder on 2016/7/21 0021.
 * 会话页面的控制类
 */
public class MsgPresenter implements MsgContract.Precenter {
    private final String TAG = MsgPresenter.class.getSimpleName();

    private final MsgContract.View msgView;

    public MsgPresenter(@NonNull MsgContract.View msgView) {
        this.msgView = msgView;
        this.msgView.setPresenter(this);
    }

    @Override
    public void start() {
        initConversationData();
    }

    @Override
    public void initConversationData() {
        msgView.showProgress();
        List<Conversation> privateConversation = new ArrayList<>();
        privateConversation.clear();
        List<BmobIMConversation> conversations = BmobIM.getInstance().loadAllConversation();
        if (conversations != null && conversations.size() > 0) {  //筛选出私聊的会话
            for (BmobIMConversation item : conversations) {
                switch (item.getConversationType()) {
                    case 1: // 私聊
                        privateConversation.add(new PrivateConversation(item));
                        break;
                    default:
                        break;
                }
            }
        }
        msgView.hideProgress(); //隐藏进度提示
        Log.d(TAG, "initConversationData: " + privateConversation.size());
        if (privateConversation != null && privateConversation.size() > 0) { //拥有私聊数据 展示会话数据
            msgView.showConversations(privateConversation);
        } else { //没有私聊会话 显示添加会话提示
            msgView.showAddConversation();
        }

    }

    @Override
    public void toAddConversation() {

    }

    @Override
    public void delConversation() {

    }
}
