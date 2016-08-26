package cn.upfinder.upfinder.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.Model.Bean.Contacts;
import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.R;

/**
 * Created by upfinder on 2016/8/9 0009.
 * 联系人列表 adapter的ViewHolder
 */
public class ContactHolder extends BaseViewHolder<Contacts> {
    @BindView(R.id.ivUserLogo)
    ImageView ivUserLogo;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserSign)
    TextView tvUserSign;

    public ContactHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_user_layout, onRecyclerViewListener);
    }

    @Override
    public void bindData(Contacts contacts) {

        Glide.with(getContext())
                .load(contacts.getUserAvatar())
                .error(R.drawable.ic_photo_loading)
                .into(ivUserLogo);
        tvUserName.setText(contacts.getNickName());
        tvUserSign.setText(contacts.getUserCount());
    }
}
