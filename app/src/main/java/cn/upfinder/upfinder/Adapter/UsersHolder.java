package cn.upfinder.upfinder.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import cn.upfinder.upfinder.Adapter.Base.BaseViewHolder;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Widget.GlideCircleTransform;

/**
 * Created by upfinder on 2016/7/29 0029.
 * 用户的ViewHolder
 */
public class UsersHolder extends BaseViewHolder<User> {
    @BindView(R.id.ivUserLogo)
    ImageView ivUserLogo;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserSign)
    TextView tvUserSign;

    public UsersHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_user_layout, onRecyclerViewListener);
    }

    @Override
    public void bindData(User user) {

        Glide.with(getContext())
                .load(user.getAvatar())
                .error(R.drawable.ic_photo_loading)
                .transform(new GlideCircleTransform(getContext())) //圆形图
                .placeholder(R.drawable.ic_photo_loading)
                .crossFade()
                .into(ivUserLogo);
        tvUserName.setText(user.getNick() + "(" + user.getUsername() + ")");
        tvUserSign.setText(user.getSign());
    }
}
