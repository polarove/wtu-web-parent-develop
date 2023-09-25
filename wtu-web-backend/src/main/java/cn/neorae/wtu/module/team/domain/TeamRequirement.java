package cn.neorae.wtu.module.team.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 新建队伍时的要求表
 * @TableName team_requirement
 */
@TableName(value ="team_requirement")
@Data
public class TeamRequirement implements Serializable {
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
     * Team UUID
     */
    @TableField(value = "team_id")
    private Integer teamId;

    /**
     * 
     */
    @TableField(value = "type")
    private String type;

    /**
     * 
     */
    @TableField(value = "content")
    private String content;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic(delval = "1", value = "0")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}