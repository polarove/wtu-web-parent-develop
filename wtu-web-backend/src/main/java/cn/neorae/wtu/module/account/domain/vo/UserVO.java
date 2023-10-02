package cn.neorae.wtu.module.account.domain.vo;

import lombok.Data;

@Data
public class UserVO {

    private String uuid;

    private String name;

    private String avatar;

    private String description;

    private Integer onlineStatus;

    private Integer server;

    private Integer level;

    private String accelerator;

    private Integer affinityBooster;

    private Integer creditBooster;

    private Integer resourceBooster;

    private Integer resourceDropRateBooster;

    private Integer modDropRateBooster;
}
