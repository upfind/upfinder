package cn.upfinder.upfinder.Adapter.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.upfinder.upfinder.Adapter.OnRecyclerViewListener;

/**
 * Created by upfinder on 2016/7/28 0028.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    protected Context context;
    public OnRecyclerViewListener onRecyclerViewListener;

    public BaseViewHolder(Context context, ViewGroup root, int layoutRes, OnRecyclerViewListener onRecyclerViewListener) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
        this.onRecyclerViewListener = onRecyclerViewListener;
        this.context = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public abstract void bindData(T t);

    @Override
    public void onClick(View v) {
        if (onRecyclerViewListener != null) {
            onRecyclerViewListener.onItemClick(getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (onRecyclerViewListener != null) {
            onRecyclerViewListener.onItemLongClick(getAdapterPosition());
        }
        return true;
    }
}
