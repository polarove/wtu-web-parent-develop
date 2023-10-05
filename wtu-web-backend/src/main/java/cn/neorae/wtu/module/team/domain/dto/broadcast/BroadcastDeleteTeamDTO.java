package cn.neorae.wtu.module.team.domain.dto.broadcast;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BroadcastDeleteTeamDTO {


    private String route;

    @NotNull(message = "teamId不能为空")
    private Integer teamId;

    @NotNull(message = "服务器不能为空")
    private Integer server;
}
