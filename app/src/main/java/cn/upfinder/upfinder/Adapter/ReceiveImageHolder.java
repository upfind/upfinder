package cn.upfinder.upfinder.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.R;

/**
 * Created by upfinder on 2016/8/2 0002.
 * 聊天页面消息列表的接受图片消息的viewHolder
 */
public class ReceiveImageHolder extends BaseViewHolder<BmobIMMessage> {

    @BindView(R.id.tvMsgTime)
    TextView tvMsgTime;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.ivMsgContent)
    ImageView ivMsgContent;
    @BindView(R.id.pbMsgLoad)
    ProgressBar pbMsgLoad;


    public ReceiveImageHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_msg_image_receive_layout, onRecyclerViewListener);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindData(BmobIMMessage message) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(message.getCreateTime());
        tvMsgTime.setText(time);

        //使用buildFromDB方法转化为指定类型的消息
        BmobIMImageMessage imageMessage = BmobIMImageMessage.buildFromDB(false, message);
        Glide.with(context)
                .load(imageMessage.getRemoteUrl())
                .into(ivMsgContent)
                .onLoadFailed(new Exception("加载失败"), context.getDrawable(R.drawable.ic_photo_loading));
    }

    public void showTime(boolean isShow) {
        tvMsgTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
