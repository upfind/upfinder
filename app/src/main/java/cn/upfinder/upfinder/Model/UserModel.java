package cn.upfinder.upfinder.Model;

import android.content.Context;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import cn.upfinder.upfinder.Model.Bean.User;

/**
 * Created by Upfinder on 2016/7/21 0021.
 * 用户管理类 本地缓存账户信息
 */
public class UserModel extends BaseModel {
    private final String TAG = UserModel.class.getSimpleName();

    private static UserModel ourInstance;

    private static User localUser;

    public static UserModel getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserModel();
        }
        return ourInstance;
    }

    public User getLocalUser() {

        if (localUser == null) {
            localUser = BmobUser.getCurrentUser(getContext(),User.class);
        }
        return localUser;
    }

    public void setLocalUser(User localUser) {
        UserModel.localUser = localUser;
    }

    //同步本地User数据至服务器
    public void syncToServer(Context context, UpdateListener updateListener) {
        getLocalUser().update(context,updateListener);
    }
}
