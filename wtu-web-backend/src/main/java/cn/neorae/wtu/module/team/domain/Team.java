package cn.neorae.wtu.module.team.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 队伍表
 * @TableName team
 */
@TableName(value ="team")
@Data
public class Team implements Serializable {
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
    @TableField(value = "uuid")
    private String uuid;

    /**
     * 区服 0：国服　１：国际服
     */
    @TableField(value = "server")
    private Integer server;

    /**
     * 频道
     */
    @TableField(value = "channel")
    private String channel;

    /**
     * 
     */
    @TableField(value = "title")
    private String title;

    /**
     * 0: private, 1: public
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 
     */
    @TableField(value = "creator_uuid")
    private String creatorUuid;

    /**
     * 0: not deleted, 1: deleted
     */
    @TableField(value = "is_deleted")
    @TableLogic(delval = "1", value = "0")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}