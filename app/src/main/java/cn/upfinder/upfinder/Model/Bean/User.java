package cn.upfinder.upfinder.Model.Bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Upfinder on 2016/7/18 0018.
 * 用户，拓展BmobUser 扩展用户头像，性别，年龄等属性
 */
public class User extends BmobUser {

    private String avatar; //头像

    private String birthday; //生日

    private String sex; //性别

    private String sign; //个性签名

    private String nick; //昵称

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
