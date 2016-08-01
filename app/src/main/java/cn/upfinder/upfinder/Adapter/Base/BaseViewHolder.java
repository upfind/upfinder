package cn.upfinder.upfinder.Adapter.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by upfinder on 2016/7/28 0028.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected Context context;

    public BaseViewHolder(Context context, ViewGroup root, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public abstract void bindData(T t);
}
