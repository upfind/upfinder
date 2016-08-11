package cn.upfinder.upfinder.Model.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by upfinder on 2016/8/10 0010.
 */

@DatabaseTable(tableName = "tb_contact")
public class Contact {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "nick_name")
    private String nickName;

    @DatabaseField(columnName = "count")
    private String userCount;

    @DatabaseField(columnName = "user_object_id")
    private String userObjectId;

    @DatabaseField(columnName = "user_avatar")
    private String userAvatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}

