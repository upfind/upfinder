package cn.upfinder.upfinder.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.upfinder.upfinder.Fragment.WelcomeFragment;
import cn.upfinder.upfinder.Presenter.WelcomePresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ActivityUtils;

public class WelcomeActivity extends AppCompatActivity {

    private WelcomeFragment fragment;
    private WelcomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        fragment = (WelcomeFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (fragment == null) {
            fragment = WelcomeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.flContent);
        }

        presenter = new WelcomePresenter(this, fragment);
    }
}
