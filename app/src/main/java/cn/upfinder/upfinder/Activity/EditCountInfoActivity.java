package cn.upfinder.upfinder.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.upfinder.upfinder.Fragment.EditCountInfoFragment;
import cn.upfinder.upfinder.Presenter.EditCountInfoPresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ActivityUtils;

public class EditCountInfoActivity extends AppCompatActivity {

    private EditCountInfoPresenter presenter;
    private EditCountInfoFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_count_info);
        fragment = (EditCountInfoFragment) getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (fragment == null) {
            fragment = EditCountInfoFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.flContent);
        presenter = new EditCountInfoPresenter(fragment);
    }
}
