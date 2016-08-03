package cn.upfinder.upfinder.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Fragment.LoginFragment;
import cn.upfinder.upfinder.Presenter.LoginPresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ActivityUtils;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getSimpleName();

    private LoginPresenter loginPresenter;
    private LoginFragment loginFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.flContent);
        }


        User bmobUser = null;
        if (bmobUser == null) {
            bmobUser = new User();
            bmobUser.setEmail("upfinder@sina.cn");
            bmobUser.setPassword("123456");
        }
        loginPresenter = new LoginPresenter(this,bmobUser, loginFragment);


    }


}
