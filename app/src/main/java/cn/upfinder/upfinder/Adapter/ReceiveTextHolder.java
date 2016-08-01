package cn.upfinder.upfinder.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.R;

/**
 * Created by upfinder on 2016/7/28 0028.
 */
public class ReceiveTextHolder extends BaseViewHolder<BmobIMMessage> {


    @BindView(R.id.tvMsgTime)
    TextView tvMsgTime;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.tvMsgContent)
    TextView tvMsgContent;

    public ReceiveTextHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.item_msg_text_receive_layout);
    }

    public void bindData(BmobIMMessage message) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(message.getCreateTime());
        tvMsgTime.setText(time);
        String content = message.getContent();
        tvMsgContent.setText(content);

    }

    public void showTime(boolean isShow) {
        tvMsgTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


}