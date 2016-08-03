package cn.upfinder.upfinder.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.upfinder.upfinder.Fragment.UserInfoFragment;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Presenter.UserInfoPresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ActivityUtils;

public class UserInfoActivity extends AppCompatActivity {
    private final String TAG = UserInfoActivity.class.getSimpleName();
    public static final String INTENT_KEY_USER = "userDataKey";

    private UserInfoPresenter presenter;
    private UserInfoFragment userInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }


        Bundle bundle = intent.getBundleExtra(INTENT_KEY_USER);
        User user = (User) bundle.getSerializable(INTENT_KEY_USER);
        if (user == null) {
            return;
        }

        userInfoFragment = (UserInfoFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (userInfoFragment == null) {
            userInfoFragment = UserInfoFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), userInfoFragment, R.id.flContent);
        presenter = new UserInfoPresenter(this, user, userInfoFragment);

    }
}
