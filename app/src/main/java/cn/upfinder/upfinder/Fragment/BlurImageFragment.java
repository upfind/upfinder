package cn.upfinder.upfinder.Fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.upfinder.upfinder.Contract.BlurImageContract;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.BlurBitmapUtil;


public class BlurImageFragment extends Fragment implements BlurImageContract.View {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tvPagerName)
    TextView tvPagerName;
    private BlurImageContract.Presenter presenter;

    @BindView(R.id.ivBlur)
    ImageView ivBlur;
    @BindView(R.id.ivOrigin)
    ImageView ivOrigin;
    @BindView(R.id.sbBlur)
    SeekBar sbBlur;
    @BindView(R.id.tvBlurProgress)
    TextView tvBlurProgress;

    //透明度
    private int alpha;

    private Bitmap tempBitmap; // 原始图片

    private Bitmap finalBitmap; //模糊后的图片


    public BlurImageFragment() {
    }


    public static BlurImageFragment newInstance() {
        BlurImageFragment fragment = new BlurImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blur_image, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.presenter.start();

        tvPagerName.setText("彩蛋");

        //获取图片
        tempBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_girl);
        finalBitmap = BlurBitmapUtil.blur(getContext(), tempBitmap);

        //填充模糊后的图片
        ivBlur.setImageBitmap(finalBitmap);
        ivOrigin.setImageBitmap(tempBitmap);

        // 处理seekbar滑动事件
        setSeekBar();
    }

    private void setSeekBar() {
        sbBlur.setMax(100);
        sbBlur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alpha = progress;
                ivOrigin.setAlpha((int) (255 - alpha * 2.55));
                tvBlurProgress.setText(String.valueOf(alpha));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void setPresenter(BlurImageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.btn_back,R.id.tvPagerName})
    public void onClick() {
        getActivity().finish();
    }
}
