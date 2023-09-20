package cn.neorae.wtu.module.account.domain.vo;

import lombok.Data;

@Data
public class UserVO {

    private String uuid;

    private String name;

    private String avatar;

    private String description;

    private Integer onlineStatus;
}
