package cn.upfinder.upfinder.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.upfinder.upfinder.Activity.HomeActivity;
import cn.upfinder.upfinder.Activity.RegisterActivity;
import cn.upfinder.upfinder.Contract.LoginContract;
import cn.upfinder.upfinder.R;

/**
 * Created by Upfinder on 2016/7/17 0017.
 */
public class LoginFragment extends Fragment implements LoginContract.View {
    private final String TAG = LoginFragment.class.getSimpleName();

    private static final int GO_TO_REGISTER = 11;
    private static final int GO_TO_FORGET_PWD = 12;

    public static final String RESULT_CODE_COUNT = "registerSuccessCount";
    public static final String RESULT_CODE_PWD = "registerSuccessPwd";

    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.etLoginCount)
    EditText etLoginCount;
    @BindView(R.id.etLoginPwd)
    EditText etLoginPwd;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.tvForgetPwd)
    TextView tvForgetPwd;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.tilCount)
    TextInputLayout tilCount;
    @BindView(R.id.tilPwd)
    TextInputLayout tilPwd;
    @BindView(R.id.pbLogin)
    ContentLoadingProgressBar pbLogin;


    //控制器
    private LoginContract.Presenter presenter;

    public LoginFragment() {
        //空构造器
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showLoginErr() {
        Toast.makeText(getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
        hideLoginProgress();
        changeBtnLoginStatus(false);
    }

    @Override
    public void showCountErr() {
        tilCount.setError(getResources().getString(R.string.count_err));
    }

    @Override
    public void showPwdErr() {
        tilPwd.setError(getResources().getString(R.string.pwd_err));
    }

    @Override
    public void showLoginSuccess() {
        Toast.makeText(getActivity(), getResources().getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        hideLoginProgress();
        changeBtnLoginStatus(false);
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showLoginProgress() {

        pbLogin.show();
    }

    @Override
    public void hideLoginProgress() {
        pbLogin.hide();
    }


    @Override
    public void changeBtnLoginStatus(boolean isLogining) {
        if (isLogining) {
            btnLogin.setClickable(false);
        } else {
            btnLogin.setClickable(true);
        }
    }

    @Override
    public void goToRegister() {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivityForResult(intent, GO_TO_REGISTER);
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.btnLogin, R.id.tvForgetPwd, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                presenter.login(etLoginCount.getText().toString(), etLoginPwd.getText().toString());
                break;
            case R.id.tvForgetPwd:

                break;
            case R.id.btnRegister:
                goToRegister();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GO_TO_REGISTER:
                if (resultCode == getActivity().RESULT_OK) {
                    String count = data.getStringExtra(RESULT_CODE_COUNT);
                    String pwd = data.getStringExtra(RESULT_CODE_PWD);
                    etLoginCount.setText(count);
                    etLoginPwd.setText(pwd);
                } else {

                }
                break;
            case GO_TO_FORGET_PWD:
                break;
        }
    }
}
