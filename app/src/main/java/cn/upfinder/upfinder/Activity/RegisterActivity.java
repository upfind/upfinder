package cn.upfinder.upfinder.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.upfinder.upfinder.Fragment.RegisterFragment;
import cn.upfinder.upfinder.Presenter.RegisterPresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ActivityUtils;

public class RegisterActivity extends AppCompatActivity {
    private final String TAG = RegisterActivity.class.getSimpleName();

    private RegisterFragment registerFragment;
    private RegisterPresenter registerPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFragment = (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.flRegister);
        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), registerFragment, R.id.flRegister);
        }
        registerPresenter = new RegisterPresenter(registerFragment);
    }
}
