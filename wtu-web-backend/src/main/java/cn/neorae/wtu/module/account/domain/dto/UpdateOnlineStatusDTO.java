package cn.neorae.wtu.module.account.domain.dto;


import lombok.Data;

@Data
public class UpdateOnlineStatusDTO {


    private String uuid;

    private Integer onlineStatus;
}
