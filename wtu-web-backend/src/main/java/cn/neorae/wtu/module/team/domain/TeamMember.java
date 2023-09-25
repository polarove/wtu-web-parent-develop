package cn.neorae.wtu.module.team.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    @TableField(value = "team_id")
    private Integer teamId;

    /**
     * 
     */
    @TableField(value = "user_uuid")
    private String userUuid;

    /**
     *
     */
    @TableField(value = "en")
    private String en;


    /**
     *
     */
    @TableField(value = "cn")
    private String cn;

    /**
     * 
     */
    @TableField(value = "focus")
    private String focus;

    /**
     * 是否为队长，0：否，1：是
     */
    @TableField(value = "leader")
    private Integer leader;

    /**
     * 是否已被选择，0：否，1：是
     */
    @TableField(value = "occupied")
    private Integer occupied;


    /**
     * 0: not deleted, 1: deleted
     */
    @TableField(value = "is_deleted")
    @TableLogic(delval = "1", value = "0")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}