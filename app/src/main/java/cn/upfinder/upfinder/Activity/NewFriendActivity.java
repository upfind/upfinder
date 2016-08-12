package cn.upfinder.upfinder.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.upfinder.upfinder.Fragment.NewFriendFragment;
import cn.upfinder.upfinder.Presenter.NewFriendPresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ActivityUtils;

public class NewFriendActivity extends AppCompatActivity {
    private final String TAG = NewFriendActivity.class.getSimpleName();

    private NewFriendPresenter presenter;
    private NewFriendFragment newFriendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);
        newFriendFragment = (NewFriendFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (newFriendFragment == null) {
            newFriendFragment = NewFriendFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), newFriendFragment, R.id.flContent);
        }

        presenter = new NewFriendPresenter(this, newFriendFragment);
    }
}
