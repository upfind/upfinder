package cn.upfinder.upfinder.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMSendStatus;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Model.UserModel;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.StringUtil;
import cn.upfinder.upfinder.Widget.GlideCircleTransform;

/**
 * Created by upfinder on 2016/7/28 0028.
 */
public class SendTextHolder extends BaseViewHolder<BmobIMMessage> {
    private final String TAG = SendTextHolder.class.getSimpleName();

    @BindView(R.id.tvMsgTime)
    TextView tvMsgTime;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvMsgContent)
    TextView tvMsgContent;
    @BindView(R.id.ivMsgFailResend)
    ImageView ivMsgFailResend;
    @BindView(R.id.tvSendStatus)
    TextView tvSendStatus;
    @BindView(R.id.pbMsgLoad)
    ProgressBar pbMsgLoad;

    private BmobIMConversation conversation;

    public SendTextHolder(Context context, ViewGroup root, BmobIMConversation conversation, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_msg_text_send_layout, onRecyclerViewListener);
        this.conversation = conversation;
    }

    public void bindData(BmobIMMessage message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = dateFormat.format(message.getCreateTime());
        String content = message.getContent();
        tvMsgContent.setText(content);
        tvMsgTime.setText(time);

        String avatarUri = UserModel.getInstance().getLocalUser().getAvatar();
        Log.d(TAG, "bindData: " + avatarUri);
        Glide.with(getContext())
                .load(avatarUri)
                .error(R.drawable.ic_photo_loading)
                .transform(new GlideCircleTransform(getContext())) //圆形图
                .placeholder(R.drawable.ic_photo_loading)
                .crossFade()
                .into(ivAvatar);

        //根据消息发送的状态显示对应的布局
        int status = message.getSendStatus();
        if (status == BmobIMSendStatus.SENDFAILED.getStatus()) {
            ivMsgFailResend.setVisibility(View.VISIBLE);
            pbMsgLoad.setVisibility(View.GONE);
        } else if (status == BmobIMSendStatus.SENDING.getStatus()) {
            ivMsgFailResend.setVisibility(View.GONE);
            pbMsgLoad.setVisibility(View.VISIBLE);
        } else {
            ivMsgFailResend.setVisibility(View.GONE);
            pbMsgLoad.setVisibility(View.GONE);
        }

    }

    public void showTime(boolean isShow) {

        tvMsgTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


}
