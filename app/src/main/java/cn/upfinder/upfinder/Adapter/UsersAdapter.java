package cn.upfinder.upfinder.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.Model.Bean.User;

/**
 * Created by upfinder on 2016/7/29 0029.
 */
public class UsersAdapter extends RecyclerView.Adapter {


    private List<User> userList = new ArrayList<>();

    //点击接口
    private OnRecyclerViewListener onRecyclerViewListener;

    public UsersAdapter() {
    }

    public void setUserList(List<User> userList) {
        if (this.userList != null) {
            this.userList.clear();
            this.userList.addAll(userList);
            notifyDataSetChanged();
        } else {
            this.userList = new ArrayList<>();
            this.userList.addAll(userList);
            notifyDataSetChanged();
        }
    }


    /*获取用户*/
    public User getItem(int position) {
        return userList.get(position);
    }

    //添加点击监听
    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsersHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((BaseViewHolder) holder).bindData(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}
