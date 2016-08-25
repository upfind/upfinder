package cn.upfinder.upfinder.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.upfinder.upfinder.Fragment.BlurImageFragment;
import cn.upfinder.upfinder.Presenter.BlurImagePresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ActivityUtils;

public class BlurImageActivity extends AppCompatActivity {


    private BlurImageFragment blurImageFragment;
    private BlurImagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        blurImageFragment = (BlurImageFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (blurImageFragment == null) {
            blurImageFragment = BlurImageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), blurImageFragment, R.id.flContent);
        }

        presenter = new BlurImagePresenter(this, blurImageFragment);
    }

}
