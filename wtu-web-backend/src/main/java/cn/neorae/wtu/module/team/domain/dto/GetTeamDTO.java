package cn.neorae.wtu.module.team.domain.dto;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

@Data
public class GetTeamDTO {

    private Integer page;

    private Integer size;

    private String channel;

    private Integer server;
}
