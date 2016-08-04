package cn.upfinder.upfinder.Model;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobListener;
import cn.upfinder.upfinder.Model.Bean.User;

/**
 * Created by Administrator on 2016/8/4 0004.
 *
 */
public abstract class QueryUserListener extends BmobListener<User> {

    public abstract void done(User s, BmobException e);

    @Override
    protected void postDone(User o, BmobException e) {
        done(o, e);
    }

}
