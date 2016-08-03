package cn.upfinder.upfinder.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.core.BmobIMClient;
import cn.upfinder.upfinder.Fragment.ChatFragment;
import cn.upfinder.upfinder.Model.Bean.Conversation;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;
import cn.upfinder.upfinder.Presenter.ChatPresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ActivityUtils;

public class ChatActivity extends AppCompatActivity {
    private final String TAG = ChatActivity.class.getSimpleName();
    public static final String INTENT_KEY_CONVERSATION = "conversation";
    private ChatPresenter chatPresenter;
    private ChatFragment chatFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        Bundle bundle = intent.getBundleExtra(INTENT_KEY_CONVERSATION);
        PrivateConversation conversation = (PrivateConversation) bundle.getSerializable(INTENT_KEY_CONVERSATION);
        BmobIMConversation bmobIMConversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), conversation.getConversation());
        chatFragment = (ChatFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (chatFragment == null) {
            chatFragment = ChatFragment.newInstance();

        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), chatFragment, R.id.flContent);
        chatPresenter = new ChatPresenter(this, chatFragment, bmobIMConversation);
    }
}
