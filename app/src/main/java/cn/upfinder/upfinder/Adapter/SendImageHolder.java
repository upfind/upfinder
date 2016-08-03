package cn.upfinder.upfinder.Adapter;

import android.content.Context;
import android.text.TextUtils;
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
import cn.bmob.newim.bean.BmobIMSendStatus;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.R;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class SendImageHolder extends BaseViewHolder<BmobIMMessage> {


    @BindView(R.id.tvMsgTime)
    TextView tvMsgTime;
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;
    @BindView(R.id.ivMsgContent)
    ImageView ivMsgContent;
    @BindView(R.id.ivFailResend)
    ImageView ivFailResend;
    @BindView(R.id.tvSendStatus)
    TextView tvSendStatus;
    @BindView(R.id.pbMsgLoad)
    ProgressBar pbMsgLoad;

    public SendImageHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_msg_image_send_layout, onRecyclerViewListener);
    }

    @Override
    public void bindData(BmobIMMessage message) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(message.getCreateTime());
        tvMsgTime.setText(time);
        //创建ImageMessage
        BmobIMImageMessage imageMessage = BmobIMImageMessage.buildFromDB(true, message);
        int status = imageMessage.getSendStatus(); //根据不同发送状态显示不同布局
        if (status == BmobIMSendStatus.SENDFAILED.getStatus() || status == BmobIMSendStatus.UPLOADAILED.getStatus()) { //发送失败
            ivFailResend.setVisibility(View.VISIBLE);
            pbMsgLoad.setVisibility(View.GONE);
            tvSendStatus.setVisibility(View.INVISIBLE);
        } else if (status == BmobIMSendStatus.SENDING.getStatus()) { // 正在发送
            pbMsgLoad.setVisibility(View.VISIBLE);
            ivFailResend.setVisibility(View.GONE);
            tvSendStatus.setVisibility(View.INVISIBLE);
        } else {
            tvSendStatus.setVisibility(View.VISIBLE);
            tvSendStatus.setText("已发送");
            ivFailResend.setVisibility(View.GONE);
            pbMsgLoad.setVisibility(View.GONE);
        }

        //若发送的是远程图片，则加载远程地址。
        //若发送的是本地图片，则加载本地图片
//        if (TextUtils.isEmpty(imageMessage.getRemoteUrl())) { //若远程地址为空 加载本地地址
//
//        } else { //否则加载远程地址
//            Glide.with(context)
//                    .load(imageMessage.getRemoteUrl())
//                    .into(ivMsgContent);
//        }

        String loadUrl = TextUtils.isEmpty(imageMessage.getRemoteUrl()) ? imageMessage.getLocalPath() : imageMessage.getRemoteUrl();
        Glide.with(context)
                .load(loadUrl)
                .error(R.drawable.ic_photo_loading)
                .into(ivMsgContent);
    }

    public void showTime(boolean isShow) {
        tvMsgTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
