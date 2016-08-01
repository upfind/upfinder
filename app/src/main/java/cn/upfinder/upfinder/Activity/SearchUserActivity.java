package cn.upfinder.upfinder.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.upfinder.upfinder.Fragment.SearchUserFragment;
import cn.upfinder.upfinder.Presenter.SearchUserPresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ActivityUtils;

public class SearchUserActivity extends AppCompatActivity {
    private static final String TAG = SearchUserActivity.class.getSimpleName();

    private SearchUserFragment fragment;
    private SearchUserPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        fragment = (SearchUserFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (fragment == null) {
            fragment = SearchUserFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.flContent);
        presenter = new SearchUserPresenter(fragment);
    }
}
