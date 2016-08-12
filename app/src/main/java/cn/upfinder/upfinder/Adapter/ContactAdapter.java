package cn.upfinder.upfinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.upfinder.upfinder.Activity.NewFriendActivity;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.R;

/**
 * Created by upfinder on 2016/8/9 0009.
 */
public class ContactAdapter extends RecyclerView.Adapter {
    private final String TAG = ContactAdapter.class.getSimpleName();

    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_FOOTER = 1;
    private static final int ITEM_TYPE_CONTENT = 2;

    private List<Friend> friendList = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;
    private Context context;

    private int headerCount = 1;
    private int footerCount = 0;


    public ContactAdapter(Context context) {
        this.context = context;
    }

    public void setFriendList(List<Friend> friendList) {
        if (friendList != null) {
            this.friendList.clear();
            this.friendList.addAll(friendList);
            notifyDataSetChanged();
        } else {
            this.friendList = new ArrayList<>();
            this.friendList.addAll(friendList);
            notifyDataSetChanged();
        }
    }

    //获取内容部分数据长度
    public int getContentItemCount() {
        return friendList.size();
    }


    //判断当前item是否是HeaderView
    private boolean isHeaderView(int position) {
        return headerCount != 0 && position < headerCount;
    }

    //判断当前item是否是FooterView
    private boolean isFooterView(int position) {
        return footerCount != 0 && position >= (headerCount + getContentItemCount());
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(parent.getContext(), parent, null);
        } else if (viewType == ITEM_TYPE_FOOTER) {
            return new HeaderViewHolder(parent.getContext(), parent, null);
        } else if (viewType == ITEM_TYPE_CONTENT) {
            return new ContactHolder(parent.getContext(), parent, onRecyclerViewListener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bindData(null);
        } else if (holder instanceof ContactHolder) {
            ((ContactHolder) holder).bindData(friendList.get(position - headerCount));
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + headerCount + footerCount;
    }

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


    /*
    *Header布局ViewHolder
     *  */
    public class HeaderViewHolder extends BaseViewHolder {

        @BindView(R.id.ivContactNewFriendDot)
        ImageView ivContactNewFriendDot;
        @BindView(R.id.llContactNewFriend)
        LinearLayout llContactNewFriend;

        public HeaderViewHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
            super(context, root, R.layout.rv_header_contact, onRecyclerViewListener);
        }

        @Override
        public void bindData(Object o) {


        }

        @OnClick(R.id.llContactNewFriend)
        public void onClick() {
            Log.d(TAG, "onClick: 启动新好友页面");

            // TODO: 启动新的好友页面
            Intent intent = new Intent(context, NewFriendActivity.class);
            context.startActivity(intent);
        }
    }
}
