package cn.upfinder.upfinder.Presenter;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.upfinder.upfinder.Contract.NewFriendContract;
import cn.upfinder.upfinder.Model.Bean.NewFriend;
import cn.upfinder.upfinder.Model.DB.Dao.NewFriendDao;

/**
 * Created by upfinder on 2016/8/12 0012.
 */
public class NewFriendPresenter implements NewFriendContract.Presenter {

    private Context context;
    private NewFriendContract.View newFriendView;

    public NewFriendPresenter(Context context, NewFriendContract.View newFriendView) {
        this.context = context;
        this.newFriendView = newFriendView;
        this.newFriendView.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void queryAllNewFriend() {
        List<NewFriend> newFriendList = new ArrayList<>();
        try {
            newFriendList = new NewFriendDao(context).query();
            if (newFriendList.size() > 0 && newFriendList != null) {
                newFriendView.showAllNewFriend(newFriendList);
            } else {

                newFriendView.showToast("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newFriendView.showToast("查询本地新朋友数据出错" + e.getMessage());
        }
    }
}
