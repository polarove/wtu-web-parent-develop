package cn.neorae.wtu.module.account.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    @TableField(value = "name", insertStrategy = FieldStrategy.DEFAULT)
    private String name;

    /**
     * Avatar
     */
    @TableField(value = "avatar", insertStrategy = FieldStrategy.DEFAULT)
    private String avatar;


    /**
     * Avatar
     */
    @TableField(value = "description", insertStrategy = FieldStrategy.DEFAULT)
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
    private Integer bonusXp;

    /**
     * Bonus XP Days Left
     */
    @TableField(value = "bonus_xp_days")
    private Integer bonusXpDays;

    /**
     * Bonus Cash 0: not available 1: available
     */
    @TableField(value = "bonus_cash")
    private Integer bonusCash;

    /**
     * Bonus Cash Days Left
     */
    @TableField(value = "bonus_cash_days")
    private Integer bonusCashDays;

    /**
     * Bonus Item 0: not available 1: available
     */
    @TableField(value = "bonus_item")
    private Integer bonusItem;

    /**
     * Bonus Item Days Left
     */
    @TableField(value = "bonus_item_days")
    private Integer bonusItemDays;

    /**
     * Bonus Drop Rate 0: not available 1: available
     */
    @TableField(value = "bonus_drop_rate")
    private Integer bonusDropRate;

    /**
     * Bonus Drop Rate Days Left
     */
    @TableField(value = "bonus_drop_rate_days")
    private Integer bonusDropRateDays;

    /**
     * 0: not verified, 1: verified
     */
    @TableField(value = "verified")
    private Integer verified;

    /**
     * Deleted 0: not deleted 1: deleted
     */
    @TableField(value = "is_deleted")
    @TableLogic(value = "0", delval = "1")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}