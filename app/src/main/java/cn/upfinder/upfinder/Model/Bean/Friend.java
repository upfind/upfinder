package cn.upfinder.upfinder.Model.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import cn.bmob.v3.BmobObject;

/**
 * Created by upfinder on 2016/8/9 0009.
 * 好友列表
 */
@DatabaseTable(tableName = "tb_friend")
public class Friend extends BmobObject {

    @DatabaseField(columnName = "user")
    private User user;
    @DatabaseField(columnName = "friend_user")
    private User friendUser;

    //拼音
    @DatabaseField(columnName = "pinyin")
    private transient String pinyin;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }


}
