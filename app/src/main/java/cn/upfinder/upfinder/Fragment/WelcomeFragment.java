package cn.upfinder.upfinder.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import cn.upfinder.upfinder.Activity.HomeActivity;
import cn.upfinder.upfinder.Activity.LoginActivity;
import cn.upfinder.upfinder.Activity.SplashActivity;
import cn.upfinder.upfinder.Contract.WelcomeContract;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.SharePreferencesUtil;


public class WelcomeFragment extends Fragment implements WelcomeContract.View {

    @BindView(R.id.banner_guide_background)
    BGABanner bannerGuideBackground;
    @BindView(R.id.banner_guide_foreground)
    BGABanner bannerGuideForeground;
    @BindView(R.id.tv_guide_skip)
    TextView tvGuideSkip;
    @BindView(R.id.btn_guide_enter)
    Button btnGuideEnter;
    private WelcomeContract.Presenter presenter;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    public static WelcomeFragment newInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        processLogic();
    }

    private void initView() {
        bannerGuideForeground.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == bannerGuideForeground.getItemCount() - 2) {
                    ViewCompat.setAlpha(btnGuideEnter, positionOffset);
                    ViewCompat.setAlpha(tvGuideSkip, 1.0f - positionOffset);

                    if (positionOffset > 0.5f) {
                        btnGuideEnter.setVisibility(View.VISIBLE);
                        tvGuideSkip.setVisibility(View.GONE);
                    } else {
                        btnGuideEnter.setVisibility(View.GONE);
                        tvGuideSkip.setVisibility(View.VISIBLE);
                    }
                } else if (position == bannerGuideForeground.getItemCount() - 1) {
                    tvGuideSkip.setVisibility(View.GONE);
                    btnGuideEnter.setVisibility(View.VISIBLE);
                    ViewCompat.setAlpha(btnGuideEnter, 1.0f);
                } else {
                    tvGuideSkip.setVisibility(View.VISIBLE);
                    ViewCompat.setAlpha(tvGuideSkip, 1.0f);
                    btnGuideEnter.setVisibility(View.GONE);
                }
            }
        });
    }


    private void processLogic() {
        bannerGuideBackground.setOverScrollMode(View.OVER_SCROLL_NEVER);
        bannerGuideForeground.setOverScrollMode(View.OVER_SCROLL_NEVER);

        // 初始化方式1：通过传入数据模型并结合Adapter的方式初始化
        bannerGuideBackground.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ((ImageView) view).setImageResource((int) model);
            }
        });
        bannerGuideBackground.setData(Arrays.asList(R.drawable.uoko_guide_background_1, R.drawable.uoko_guide_background_2, R.drawable.uoko_guide_background_3), null);


        // 初始化方式2：通过直接传入视图集合的方式初始化
        List<View> views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(getContext(), R.drawable.uoko_guide_foreground_1));
        views.add(BGABannerUtil.getItemImageView(getContext(), R.drawable.uoko_guide_foreground_2));
        views.add(BGABannerUtil.getItemImageView(getContext(), R.drawable.uoko_guide_foreground_3));
        bannerGuideForeground.setData(views);
    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.tv_guide_skip, R.id.btn_guide_enter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_guide_skip:
            case R.id.btn_guide_enter:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 在界面可见时给背景Banner设置一个白色背景，避免滑动过程中两个Banner都设置透明度后能看到Launcher
        bannerGuideBackground.setBackgroundResource(android.R.color.white);
    }
}
