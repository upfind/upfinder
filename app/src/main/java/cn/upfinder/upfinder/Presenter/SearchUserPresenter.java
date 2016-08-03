package cn.upfinder.upfinder.Presenter;

import android.content.Context;
import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.upfinder.upfinder.Contract.SearchUserContract;
import cn.upfinder.upfinder.Model.Bean.User;

/**
 * Created by upfinder on 2016/7/23 0023.
 * 搜索用户控制类
 */
public class SearchUserPresenter implements SearchUserContract.Presenter {
    private static final String TAG = SearchUserPresenter.class.getSimpleName();

    private SearchUserContract.View searchUserView;
    private Context context;

    public SearchUserPresenter(Context context, SearchUserContract.View searchUserView) {
        this.context = context;
        this.searchUserView = searchUserView;
        searchUserView.setPresenter(this);
    }

    @Override
    public void start() {
        searchUserView.initView();
    }

    @Override
    public void searchUser(String userName) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("username", userName);

        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) { //成功搜索到用户
                Log.d(TAG, "done: 共搜索到" + list.size() + "用户");
                searchUserView.showSearchResult(list);
            }

            @Override
            public void onError(int i, String s) {
                searchUserView.showErrMsg("错误码：" + i + "：" + s);
            }


        });
    }
}
