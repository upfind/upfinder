package cn.upfinder.upfinder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.R;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class NewFriendAdapter extends RecyclerView.Adapter {

    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_FOOTER = 2;

    private LayoutInflater layoutInflater;
    private Context context;
    private int headerCount = 1; //头部布局View个数
    private int footerCount = 1; //底部布局View个数

    private List<Friend> friendList = new ArrayList<>();

    public NewFriendAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }

    //获取内容部分数据长度
    public int getContentItemCount() {
        return friendList.size();
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return headerCount != 0 && position < headerCount;
    }

    //判断当前item是否是FooterView
    public boolean isFooterView(int position) {
        return footerCount != 0 && position >= (headerCount + getContentItemCount());
    }

    //判断当前item类型


    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return ITEM_TYPE_HEADER;
        } else if (isFooterView(position)) {
            return ITEM_TYPE_FOOTER;
        } else {
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {

            return new HeaderViewHolder(parent.getContext(), parent, null);
        } else if (viewType == ITEM_TYPE_FOOTER) {

            return new FooterViewHolder(parent.getContext(), parent, null);
        } else if (viewType == ITEM_TYPE_CONTENT) {

            return new ContentViewHolder(parent.getContext(), parent, null);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bindData(null);
        } else if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).bindData(null);
        } else if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder) holder).bindData(friendList.get(position));
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + footerCount + headerCount;
    }

    /*
    *Content部分ViewHolder
     * */
    private class ContentViewHolder extends BaseViewHolder<Friend> {

        @BindView(R.id.ivNewFriendLogo)
        ImageView ivNewFriendLogo;
        @BindView(R.id.tvNewFriendName)
        TextView tvNewFriendName;
        @BindView(R.id.tvNewFriendSign)
        TextView tvNewFriendSign;
        @BindView(R.id.btnNewFriendActive)
        Button btnNewFriendActive;

        public ContentViewHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
            super(context, root, R.layout.item_new_friend, onRecyclerViewListener);
        }

        @Override
        public void bindData(Friend friend) {

        }

        @OnClick({R.id.ivNewFriendLogo, R.id.btnNewFriendActive})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivNewFriendLogo:
                    break;
                case R.id.btnNewFriendActive:
                    break;
            }
        }
    }


    /*
    *Header布局ViewHolder
     *  */
    private class HeaderViewHolder extends BaseViewHolder {

        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.textView2)
        TextView textView2;
        @BindView(R.id.textView3)
        TextView textView3;

        public HeaderViewHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
            super(context, root, R.layout.layout_newfriend_header, onRecyclerViewListener);
        }

        @Override
        public void bindData(Object o) {

            textView.setText("新好友");
            textView2.setText("群消息");
            textView3.setText("公众号");
        }
    }

    /*
    * footer布局ViewHolder
    * */
    private class FooterViewHolder extends BaseViewHolder {

        public FooterViewHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
            super(context, root, R.layout.layout_newfriend_header, onRecyclerViewListener);
        }

        @Override
        public void bindData(Object o) {

        }
    }
}
