package cn.neorae.wtu.module.account.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
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
     * UUID
     */
    @TableField(value = "uuid")
    private String uuid;

    /**
     * email
     */
    @TableField(value = "email")
    private String email;

    /**
     * password
     */
    @TableField(value = "password")
    private String password;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * Avatar
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * description
     */
    @TableField(value = "description")
    private String description;

    /**
     * Clan ID
     */
    @TableField(value = "clan_id")
    private Integer clanId;

    /**
     * Rank
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * Online Status 0: offline 1: online 2: online-in-game
     */
    @TableField(value = "online_status")
    private Integer onlineStatus;

    /**
     * Bonus XP 0: not available 1: available
     */
    @TableField(value = "affinity_booster")
    private Integer affinityBooster;

    /**
     * Bonus Cash 0: not available 1: available
     */
    @TableField(value = "credit_booster")
    private Integer creditBooster;

    /**
     * Bonus Item 0: not available 1: available
     */
    @TableField(value = "resource_booster")
    private Integer resourceBooster;

    /**
     * Bonus Drop Rate 0: not available 1: available
     */
    @TableField(value = "resource_drop_rate_booster")
    private Integer resourceDropRateBooster;

    /**
     * mod_droprate_booster 0: unavailable 1: available
     */
    @TableField(value = "mod_drop_rate_booster")
    private Integer modDropRateBooster;

    /**
     * 0: 国服, 1: 国际服
     */
    @TableField(value = "server")
    private Integer server;


    /**
     * 0: not verified, 1: verified
     */
    @TableField(value = "verified")
    private Integer verified;

    /**
     * Deleted 0: not deleted 1: deleted
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}