package cn.neorae.wtu.module.team.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class UserBO {

    private String uuid;

    private String name;

    private String avatar;

    private Integer onlineStatus;

    private List<String> boosterList;

}
