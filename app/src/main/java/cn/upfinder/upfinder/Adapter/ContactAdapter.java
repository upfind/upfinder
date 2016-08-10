package cn.upfinder.upfinder.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.Model.Bean.Friend;

/**
 * Created by upfinder on 2016/8/9 0009.
 */
public class ContactAdapter extends RecyclerView.Adapter {

    private List<Friend> friendList = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public ContactAdapter() {
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

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((BaseViewHolder) holder).bindData(friendList.get(position));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }
}
