package cn.upfinder.upfinder.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.upfinder.upfinder.Contract.UserInfoContract;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Presenter.UserInfoPresenter;
import cn.upfinder.upfinder.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment implements UserInfoContract.View {
    private final String TAG = UserInfoFragment.class.getSimpleName();

    @BindView(R.id.ivCountUserLogo)
    ImageView ivCountUserLogo;
    @BindView(R.id.tvCountUserNick)
    TextView tvCountUserNick;
    @BindView(R.id.tvCountUserNum)
    TextView tvCountUserNum;
    @BindView(R.id.llCountInfo)
    LinearLayout llCountInfo;
    @BindView(R.id.tvCountUserSign)
    TextView tvCountUserSign;
    @BindView(R.id.llSign)
    LinearLayout llSign;
    @BindView(R.id.btnSendMsg)
    Button btnSendMsg;
    @BindView(R.id.btnAddFriends)
    Button btnAddFriends;

    private UserInfoContract.Presenter presenter;

    private UserInfoFragment() {
        // Required empty public constructor
    }

    public static UserInfoFragment newInstance() {
        UserInfoFragment fragment = new UserInfoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setPresenter(UserInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.ivCountUserLogo, R.id.btnSendMsg, R.id.btnAddFriends})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCountUserLogo:
                break;
            case R.id.btnSendMsg:
                break;
            case R.id.btnAddFriends:
                break;
        }
    }

    @Override
    public void initViewShow(User user) {
        tvCountUserNick.setText(user.getNick());
        tvCountUserNum.setText("账号：" + user.getUsername());
        tvCountUserSign.setText(user.getSign());
        Glide.with(getActivity())
                .load(user.getAvatar())
                .error(R.drawable.ic_photo_loading)
                .into(ivCountUserLogo);
    }
}