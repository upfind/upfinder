package cn.upfinder.upfinder.Adapter.Base;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;
import cn.upfinder.upfinder.Adapter.OnRecyclerViewListener;
import cn.upfinder.upfinder.Model.Bean.Conversation;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;
import cn.upfinder.upfinder.R;

/**
 * Created by upfinder on 2016/8/4 0004.
 */
public class ConversationHolder extends BaseViewHolder<Conversation> {
    private final String TAG = ConversationHolder.class.getSimpleName();

    @BindView(R.id.ivMsgFromLogo)
    ImageView ivMsgFromLogo;
    @BindView(R.id.tvMsgFrom)
    TextView tvMsgFrom;
    @BindView(R.id.tvMsgAbstract)
    TextView tvMsgAbstract;
    @BindView(R.id.tvMsgTime)
    TextView tvMsgTime;

    public ConversationHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_msg_layout, onRecyclerViewListener);
    }

    @Override
    public void bindData(Conversation c) {

        final PrivateConversation conversation = (PrivateConversation) c;


        Glide.with(context)
                .load(conversation.getAvatar())
                .error(R.drawable.ic_photo_loading)
                .into(ivMsgFromLogo);
        tvMsgFrom.setText(conversation.getcName());
        Log.d(TAG, "onBindViewHolder: " + conversation.getcName());
        tvMsgAbstract.setText(conversation.getLastMessageContent());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        tvMsgTime.setText(dateFormat.format(conversation.getLastMessageTime()));
        itemView.setTag(conversation);
    }

    @OnClick(R.id.ivMsgFromLogo)
    public void onClick() {
        Log.d(TAG, "onClick: 点击头像" + getLayoutPosition());
    }
}
