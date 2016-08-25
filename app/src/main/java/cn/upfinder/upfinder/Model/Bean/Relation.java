package cn.upfinder.upfinder.Model.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by upfinder on 2016/8/19 0019.
 * 两人间的关系数据
 * statu 表示关系状态 1:表示对方添加请求添加 2：请求添加对方 3：成功添加好友
 */
@DatabaseTable(tableName = "tb_relation")
public class Relation {

    public static final int ASK_ADD_ME = 1;
    public static final int I_ASK_ADD = 2;
    public static final int HAS_FRIEND = 3;

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "status")
    private int status;
    @DatabaseField(columnName = "targetid")
    private String targetId;

    public Relation(String targetId, int status) {
        if (targetId.isEmpty()) {
            new IllegalAccessException("targetId 不能为空");
        }
        this.targetId = targetId;
        this.status = status;

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
