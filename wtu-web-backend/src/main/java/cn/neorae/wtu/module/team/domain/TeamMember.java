package cn.neorae.wtu.module.team.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 创建队伍时的队员信息
 * @TableName team_member
 */
@TableName(value ="team_member")
@Data
public class TeamMember implements Serializable {
    /**
     * Primary Key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Create Time
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * Update Time
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 
     */
    @TableField(value = "team_uuid")
    private String teamUuid;

    /**
     * 
     */
    @TableField(value = "user_uuid")
    private String userUuid;

    /**
     * warframe id
     */
    @TableField(value = "warframe_id")
    private Integer warframeId;

    /**
     * 
     */
    @TableField(value = "focus")
    private String focus;

    /**
     * 队中角色, 0为队长，1/2/3为队员
     */
    @TableField(value = "role")
    private Integer role;


    /**
     * 0: not deleted, 1: deleted
     */
    @TableField(value = "is_deleted")
    @TableLogic(delval = "1", value = "0")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}