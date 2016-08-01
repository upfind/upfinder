package cn.upfinder.upfinder.Contract;

import cn.upfinder.upfinder.Fragment.BaseView;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Presenter.BasePresenter;

/**
 * Created by upfinder on 2016/7/22 0022.
 * 编辑账户信息页面协议类
 */
public interface EditCountInfoContract {

    interface View extends BaseView<EditCountInfoContract.Presenter> {

        //编辑头像
        void toEditUserLogo();

        //编辑昵称
        void toEditUserNick();

        //编辑性别
        void toEditUserSex();

        //编辑签名
        void toEditUserSign();

        //显示上传进度
        void showUploadProgress(Integer value);

        //显示上传文件失败提示
        void showUploadErr(String errMsg);

        //显示头像
        void showUserLogo(String filePath);

        //显示昵称
        void showUserNick(String nickName);

        //显示签名
        void showUserSign(String userSign);

        //初始化显示账户信息
        void initView(User user);

        //显示修改失败提示
        void showEditErr(String errMsg);

    }

    interface Presenter extends BasePresenter {

        //上传头像
        void uploadUserLogo(String filePath);

        //上传更新昵称
        void uploadNickName(String nickName);

        //上传更新签名
        void uploadUserSign(String userSign);

    }
}
