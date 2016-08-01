package cn.upfinder.upfinder.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.upfinder.upfinder.Activity.EditActivity;
import cn.upfinder.upfinder.Activity.PhotoPickerActivity;
import cn.upfinder.upfinder.Contract.EditCountInfoContract;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Model.UserModel;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ImageLoader;
import cn.upfinder.upfinder.Utils.ScreenUtils;
import cn.upfinder.upfinder.Utils.ToastUtil;

/**
 * Created by upfinder on 2016/7/21 0021.
 * 编辑用户信息页面
 */
public class EditCountInfoFragment extends Fragment implements EditCountInfoContract.View {
    private final String TAG = EditCountInfoFragment.class.getSimpleName();

    private static final int PICK_PHOTO = 1;
    private static final int EDIT_NICKNAME = 2;
    private static final int EDIT_SIGN = 3;

    @BindView(R.id.ivCountUserLogo)
    ImageView ivCountUserLogo;
    @BindView(R.id.llEditUserLogo)
    LinearLayout llEditUserLogo;
    @BindView(R.id.tvCountUserNick)
    TextView tvCountUserNick;
    @BindView(R.id.llEditUserNick)
    LinearLayout llEditUserNick;
    @BindView(R.id.tvCountUserSign)
    TextView tvCountUserSign;
    @BindView(R.id.llEditUserSign)
    LinearLayout llEditUserSign;
    @BindView(R.id.tvCountNum)
    TextView tvCountNum;
    @BindView(R.id.tvCountUserSex)
    TextView tvCountUserSex;

    private EditCountInfoContract.Presenter presenter;

    public static EditCountInfoFragment newInstance() {
        return new EditCountInfoFragment();
    }

    public EditCountInfoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_count_info, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(EditCountInfoContract.Presenter presenter) {

        this.presenter = presenter;
    }

    @OnClick({R.id.ivCountUserLogo, R.id.llEditUserLogo, R.id.llEditUserNick, R.id.llEditUserSign})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCountUserLogo:
                break;
            case R.id.llEditUserLogo:
                toEditUserLogo();
                break;
            case R.id.llEditUserNick:
                toEditUserNick();
                break;
            case R.id.llEditUserSign:
                toEditUserSign();
                break;
        }
    }

    @Override
    public void toEditUserLogo() {

        //定义选择照片模式为单选
        int selectedMode = PhotoPickerActivity.MODE_SINGLE;
        //设置显示相机
        boolean showCamera = true;
        //选择最大照片张数默认，也可不设置
        int maxNum = PhotoPickerActivity.DEFAULT_NUM;
        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, showCamera);
        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, selectedMode);
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, maxNum);
        startActivityForResult(intent, PICK_PHOTO);
    }

    @Override
    public void toEditUserNick() {

        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra(EditActivity.INTENT_KEY_EDIT_CONTENT, tvCountUserNick.getText());
        startActivityForResult(intent, EDIT_NICKNAME);
    }

    @Override
    public void toEditUserSex() {

    }

    @Override
    public void toEditUserSign() {

        Intent intent = new Intent(getActivity(), EditActivity.class);
        intent.putExtra(EditActivity.INTENT_KEY_EDIT_CONTENT, tvCountUserSign.getText());
        startActivityForResult(intent, EDIT_SIGN);
    }

    @Override
    public void showUploadProgress(Integer value) {

        Log.d(TAG, "showUploadProgress: 上传进度" + value);
    }

    @Override
    public void showUploadErr(String errMsg) {
        ToastUtil.showShort(getActivity(), errMsg);
    }

    @Override
    public void showUserLogo(String filePath) {
        if (filePath.isEmpty()) {

            Glide.with(getActivity())
                    .load(UserModel.getInstance().getLocalUser().getAvatar())
                    .error(R.drawable.img_account_box_grey_600_48dp)
                    .into(ivCountUserLogo);

        } else {
            int screenWidth = ScreenUtils.getScreenW(getActivity().getApplicationContext());
            int mColumnWidth = (screenWidth - ScreenUtils.dp2px(getActivity().getApplicationContext(), 4)) / 3;
            ImageLoader.getInstance().display(filePath, ivCountUserLogo, mColumnWidth, mColumnWidth);
        }
    }

    @Override
    public void showUserNick(String nickName) {
        tvCountUserNick.setText(nickName);
    }

    @Override
    public void showUserSign(String userSign) {

        tvCountUserSign.setText(userSign);
    }

    @Override
    public void initView(User user) {
        Glide.with(getActivity())
                .load(user.getAvatar())
                .error(R.drawable.img_account_box_grey_600_48dp)
                .into(ivCountUserLogo);
        tvCountUserNick.setText(user.getNick());
        tvCountUserSign.setText(user.getSign());
        tvCountUserSex.setText(user.getSex());
        tvCountNum.setText(user.getUsername());
    }

    @Override
    public void showEditErr(String errMsg) {
        ToastUtil.showShort(getActivity(), errMsg);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO) {
            if (resultCode == getActivity().RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                String filePath = result.get(0); //获取选择图片的文件路径
                Log.e(TAG, "onActivityResult: " + filePath);
                presenter.uploadUserLogo(filePath); //上传文件
            }
        }

        if (requestCode == EDIT_NICKNAME) { //编辑昵称

            if (resultCode == getActivity().RESULT_OK) {
                String nickName = data.getStringExtra(EditActivity.RESULT_KEY_EDIT_CONTENT);
                presenter.uploadNickName(nickName);
            }
        }

        if (requestCode == EDIT_SIGN) { //编辑签名

            if (resultCode == getActivity().RESULT_OK) {
                String userSign = data.getStringExtra(EditActivity.RESULT_KEY_EDIT_CONTENT);
                presenter.uploadUserSign(userSign);
            }
        }
    }
}
