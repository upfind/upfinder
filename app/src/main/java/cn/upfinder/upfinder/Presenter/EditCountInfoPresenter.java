package cn.upfinder.upfinder.Presenter;

import android.util.Log;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
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

    public EditCountInfoPresenter(EditCountInfoContract.View editCountInfoView) {
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
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) { //上传成功 1.删除服务器中老的头像文件 2.更改本地缓存账户头像地址
                    String userLogoFileUrl = bmobFile.getFileUrl();
                    BmobFile oldFile = new BmobFile();
                    oldFile.setUrl(UserModel.getInstance().getLocalUser().getAvatar());
                    oldFile.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                            if (e == null) {
                                Log.d(TAG, "done: 删除老头像成功");
                            } else {

                                Log.e(TAG, "done: 删除老头像失败");
                            }
                        }
                    });

                    UserModel.getInstance().getLocalUser().setAvatar(userLogoFileUrl);
                    UserModel.getInstance().syncToServer(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) { //更新账户数据成功
                                editCountInfoView.showUserLogo("");
                            } else {
                                editCountInfoView.showUploadErr("更新头像失败");
                            }
                        }
                    });


                } else { //上传失败
                    editCountInfoView.showUploadErr("上传头像失败 错误码：" + e.getErrorCode() + "错误信息：" + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value) { //返回上传进度
                super.onProgress(value);
                editCountInfoView.showUploadProgress(value);
            }

        });
    }

    @Override
    public void uploadNickName(String nickName) {
        UserModel.getInstance().getLocalUser().setNick(nickName);
        UserModel.getInstance().syncToServer(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) { //上传修改昵称成功
                    editCountInfoView.showUserNick(UserModel.getInstance().getLocalUser().getNick());
                } else {
                    editCountInfoView.showEditErr("昵称修改失败！");
                }
            }
        });
    }

    @Override
    public void uploadUserSign(String userSign) {

        UserModel.getInstance().getLocalUser().setSign(userSign);
        UserModel.getInstance().syncToServer(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) { // 修改上传签名成功

                    editCountInfoView.showUserSign(UserModel.getInstance().getLocalUser().getSign());
                } else {
                    editCountInfoView.showEditErr("签名修改失败！");
                }
            }
        });
    }
}
