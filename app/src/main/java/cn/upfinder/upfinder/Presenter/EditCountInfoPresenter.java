package cn.upfinder.upfinder.Presenter;

import android.content.Context;
import android.util.Log;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.upfinder.upfinder.Contract.EditCountInfoContract;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Model.UserModel;
import cn.upfinder.upfinder.Utils.StringUtil;

/**
 * Created by upfinder on 2016/7/22 0022.
 * 编辑账户信息控制类
 */
public class EditCountInfoPresenter implements EditCountInfoContract.Presenter {
    private final String TAG = EditCountInfoPresenter.class.getSimpleName();


    private EditCountInfoContract.View editCountInfoView;
    private Context context;

    public EditCountInfoPresenter(Context context, EditCountInfoContract.View editCountInfoView) {
        this.context = context;
        this.editCountInfoView = editCountInfoView;
        editCountInfoView.setPresenter(this);
    }


    @Override
    public void start() {

        User user = UserModel.getInstance().getLocalUser();
        editCountInfoView.initView(user);
    }

    @Override
    public void uploadUserLogo(final String filePath) {

        final BmobFile bmobFile = new BmobFile(new File(filePath));

        bmobFile.uploadblock(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                String userLogoFileUrl = bmobFile.getFileUrl(context);
                BmobFile oldFile = new BmobFile();
                oldFile.setUrl(UserModel.getInstance().getLocalUser().getAvatar());
                oldFile.delete(context, new DeleteListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

                UserModel.getInstance().getLocalUser().setAvatar(userLogoFileUrl);
                UserModel.getInstance().syncToServer(context, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        editCountInfoView.showUserLogo("");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        editCountInfoView.showUploadErr("更新头像失败");
                    }

                });
            }

            @Override
            public void onFailure(int i, String s) {
                editCountInfoView.showUploadErr("上传头像失败 错误码：" + i + "错误信息：" + s);
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                editCountInfoView.showUploadProgress(value);
            }
        });


    }

    @Override
    public void uploadNickName(String nickName) {
        UserModel.getInstance().getLocalUser().setNick(nickName);
        UserModel.getInstance().syncToServer(context, new UpdateListener() {
            @Override
            public void onSuccess() {//上传修改昵称成功
                editCountInfoView.showUserNick(UserModel.getInstance().getLocalUser().getNick());
            }

            @Override
            public void onFailure(int i, String s) {
                editCountInfoView.showEditErr("昵称修改失败！");
            }

        });
    }

    @Override
    public void uploadUserSign(String userSign) {

        UserModel.getInstance().getLocalUser().setSign(userSign);
        UserModel.getInstance().syncToServer(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                editCountInfoView.showUserSign(UserModel.getInstance().getLocalUser().getSign());
            }

            @Override
            public void onFailure(int i, String s) {
                editCountInfoView.showEditErr("签名修改失败！");
            }

        });
    }
}
