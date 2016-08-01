package cn.upfinder.upfinder.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import cn.upfinder.upfinder.Contract.RegisterContract;
import cn.upfinder.upfinder.Presenter.RegisterPresenter;
import cn.upfinder.upfinder.R;


public class RegisterFragment extends Fragment implements RegisterContract.View {
    private final String TAG = RegisterFragment.class.getSimpleName();

    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.etRegisterCount)
    EditText etRegisterCount;
    @BindView(R.id.tilCount)
    TextInputLayout tilCount;
    @BindView(R.id.etregisterPwd)
    EditText etregisterPwd;
    @BindView(R.id.tilPwd)
    TextInputLayout tilPwd;
    @BindView(R.id.etRegisterPwdConfirm)
    EditText etRegisterPwdConfirm;
    @BindView(R.id.tilPwdConfirm)
    TextInputLayout tilPwdConfirm;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.tvHaveCount)
    TextView tvHaveCount;
    @BindView(R.id.pbRegister)
    ContentLoadingProgressBar pbRegister;


    //控制器
    private RegisterContract.Presenter presenter;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.btnRegister, R.id.tvHaveCount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegister:
                presenter.register(etRegisterCount.getText().toString(), etregisterPwd.getText().toString(), etRegisterPwdConfirm.getText().toString());
                break;
            case R.id.tvHaveCount:
                break;
        }
    }


    @Override
    public void showRegisterCountErr(String errMsg) {
        tilCount.setError(errMsg);
    }

    @Override
    public void showRegisterPwdErr(String errMsg) {

        tilPwd.setError(errMsg);
    }

    @Override
    public void showRegisterPwdConfirmErr() {
        tilPwdConfirm.setError(getResources().getString(R.string.pwd_confirm_err));
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(getActivity(), getResources().getString(R.string.register_success), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(LoginFragment.RESULT_CODE_COUNT, etRegisterCount.getText().toString());
        intent.putExtra(LoginFragment.RESULT_CODE_PWD, etregisterPwd.getText().toString());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void registerFailed(String errMsg) {

        Toast.makeText(getActivity(), getResources().getString(R.string.register_failed) + errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterProgress() {

        pbRegister.show();

    }

    @Override
    public void hideRegisterProgress() {
        pbRegister.hide();
    }

    @Override
    public void changeRegisterBtnStatus(boolean isRegister) {
        if (isRegister) {
            btnRegister.setClickable(false);
            btnRegister.setBackgroundResource(R.color.text_gray);
        } else {
            btnRegister.setClickable(true);
            btnRegister.setBackgroundResource(R.color.colorPrimary);
        }
    }

    @Override
    public void haveCount() {
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
