package cn.upfinder.upfinder.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.upfinder.upfinder.Activity.BlurImageActivity;
import cn.upfinder.upfinder.Activity.EditCountInfoActivity;
import cn.upfinder.upfinder.Activity.LoginActivity;
import cn.upfinder.upfinder.Activity.PictureActivity;
import cn.upfinder.upfinder.Contract.CountContract;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Widget.GlideRoundTransform;


public class CountFragment extends Fragment implements CountContract.View {
    private final String TAG = CountFragment.class.getSimpleName();

    @BindView(R.id.ivCountUserLogo)
    ImageView ivCountUserLogo;
    @BindView(R.id.tvCountUserNick)
    TextView tvCountUserNick;
    @BindView(R.id.tvCountNum)
    TextView tvCountNum;
    @BindView(R.id.ivCountUserQR)
    ImageView ivCountUserQR;
    @BindView(R.id.llCountInfo)
    LinearLayout llCountInfo;
    @BindView(R.id.llSetting)
    LinearLayout llSetting;
    @BindView(R.id.llExit)
    LinearLayout llExit;
    @BindView(R.id.llBlurImage)
    LinearLayout llBlurImage;

    private CountContract.Presenter presenter;

    public CountFragment() {
        // Required empty public constructor
    }

    public static CountFragment newInstance() {
        CountFragment fragment = new CountFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_count, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(CountContract.Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void showCountInfo(User user) {
        Glide.with(getActivity())
                .load(user.getAvatar())
                .transform(new GlideRoundTransform(getContext(), 20))
                .placeholder(R.drawable.ic_photo_loading)
                .error(R.drawable.img_account_box_grey_600_48dp)
                .crossFade()
                .into(ivCountUserLogo);
        tvCountUserNick.setText(user.getNick());
        tvCountNum.setText(user.getUsername());
    }

    @Override
    public void toEditCountInfo() {
        Intent intent = new Intent(getActivity(), EditCountInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void showCountUserLogo(User user) {
        //动画相关
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ivCountUserLogo, PictureActivity.TRANSIT_PIC);
        Intent intent = PictureActivity.newIntent(getContext(), user.getAvatar(), user.getNick());

        try {
            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }

    }

    @Override
    public void jumpToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick({R.id.ivCountUserLogo, R.id.ivCountUserQR, R.id.llCountInfo, R.id.llSetting, R.id.llExit, R.id.llBlurImage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCountUserLogo:

                presenter.jumpToPicture();
                break;
            case R.id.ivCountUserQR:
                break;
            case R.id.llCountInfo:
                toEditCountInfo();
                break;
            case R.id.llSetting:
                break;
            case R.id.llExit:
                presenter.logOut();
                break;
            case R.id.llBlurImage: //跳转到模糊处理图片页面
                Intent intent = new Intent(getContext(), BlurImageActivity.class);
                startActivity(intent);
                break;
        }
    }

}
