package cn.upfinder.upfinder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.Adapter.Base.ConversationHolder;
import cn.upfinder.upfinder.Model.Bean.Conversation;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.R;

/**
 * Created by upfinder on 2016/7/21 0021.
 */
public class ConversationAdapter extends RecyclerView.Adapter {
    private final String TAG = ConversationAdapter.class.getSimpleName();

    private Context context;
    private List<Conversation> conversations;
    private OnRecyclerViewListener onRecyclerViewListener;

    public ConversationAdapter(Context context, List<Conversation> conversations) {
        this.conversations = conversations;
        this.context = context;
    }


    /*获取用户*/
    public Conversation getItem(int position) {
        return conversations.get(position);
    }

    /*删除数据*/
    public void removeItem(int position) {
        int more = getItemCount() - conversations.size();
        conversations.remove(position - more);
        notifyDataSetChanged();
    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }


    /*刷新数据*/
    public void refreshData(List<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ConversationHolder(parent.getContext(), parent, onRecyclerViewListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((BaseViewHolder) holder).bindData(conversations.get(position));
    }


    @Override
    public int getItemCount() {
        return conversations.size();
    }


}
