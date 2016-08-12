package cn.upfinder.upfinder.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.upfinder.upfinder.Activity.SearchUserActivity;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.Model.Bean.NewFriend;
import cn.upfinder.upfinder.R;

/**
 * Created by upfinder on 2016/8/11 0011.
 */
public class NewFriendAdapter extends RecyclerView.Adapter {
    private final String TAG = NewFriendAdapter.class.getSimpleName();

    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_FOOTER = 2;

    private LayoutInflater layoutInflater;
    private Context context;
    private int headerCount = 1; //头部布局View个数
    private int footerCount = 0; //底部布局View个数

    private List<NewFriend> newFriendList = new ArrayList<>();

    public NewFriendAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setFriendList(List<NewFriend> newFriendList) {
        this.newFriendList = newFriendList;
    }

    //获取内容部分数据长度
    public int getContentItemCount() {
        return newFriendList.size();
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return headerCount != 0 && position < headerCount;
    }

    //判断当前item是否是FooterView
    public boolean isFooterView(int position) {
        return footerCount != 0 && position >= (headerCount + getContentItemCount());
    }

    //处理item类型
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
            ((ContentViewHolder) holder).bindData(newFriendList.get(position));
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
    public class ContentViewHolder extends BaseViewHolder<NewFriend> {

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
        public void bindData(NewFriend newFriend) {

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
    public class HeaderViewHolder extends BaseViewHolder {

        @BindView(R.id.etSearch)
        EditText etSearch;

        public HeaderViewHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
            super(context, root, R.layout.rv_header_search, onRecyclerViewListener);
        }

        @Override
        public void bindData(Object o) {


        }

        @OnClick(R.id.etSearch)
        public void onClick() {
            Log.d(TAG, "onClick: 跳转到搜索好友页");
            //TODO: 进行搜索操作
//            Intent intent = new Intent(context, SearchUserActivity.class);
//            ((Activity) context).startActivity(intent);
        }
    }

    /*
    * footer布局ViewHolder
    * */
    public class FooterViewHolder extends BaseViewHolder {

        public FooterViewHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
            super(context, root, R.layout.rv_header_contact, onRecyclerViewListener);
        }

        @Override
        public void bindData(Object o) {

        }
    }
}
