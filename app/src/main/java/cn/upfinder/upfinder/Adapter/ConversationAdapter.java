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
import cn.upfinder.upfinder.Model.Bean.Conversation;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.R;

/**
 * Created by upfinder on 2016/7/21 0021.
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> implements View.OnClickListener {
    private final String TAG = ConversationAdapter.class.getSimpleName();

    private Context context;
    private List<Conversation> conversations;
    private OnItemClickListener onItemClickListener;

    public ConversationAdapter(Context context, List<Conversation> conversations, OnItemClickListener onItemClickListener) {
        this.conversations = conversations;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    /*刷新数据*/
    public void refreshData(List<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final PrivateConversation conversation = (PrivateConversation) conversations.get(position);


        Glide.with(context)
                .load(conversation.getAvatar())
                .error(R.drawable.ic_photo_loading)
                .into(holder.ivMsgFromLogo);
        holder.tvMsgFrom.setText(conversation.getcName());
        Log.d(TAG, "onBindViewHolder: " + conversation.getcName());
        holder.tvMsgAbstract.setText(conversation.getLastMessageContent());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        holder.tvMsgTime.setText(dateFormat.format(conversation.getLastMessageTime()));
        holder.itemView.setTag(conversation);
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClick(v, (Conversation) v.getTag());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivMsgFromLogo)
        ImageView ivMsgFromLogo;
        @BindView(R.id.tvMsgFrom)
        TextView tvMsgFrom;
        @BindView(R.id.tvMsgAbstract)
        TextView tvMsgAbstract;
        @BindView(R.id.tvMsgTime)
        TextView tvMsgTime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, Conversation conversation);
    }

}
