package cn.neorae.wtu.module.team.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName team_warframe
 */
@TableName(value ="team_warframe")
@Data
public class TeamWarframe implements Serializable {
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
     * 英文名称
     */
    @TableField(value = "en")
    private String en;

    /**
     * 中文名称
     */
    @TableField(value = "cn")
    private String cn;

    /**
     * 是否删除 0:未删除 1:已删除
     */
    @TableField(value = "is_deleted")
    @TableLogic(delval = "1", value = "0")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}