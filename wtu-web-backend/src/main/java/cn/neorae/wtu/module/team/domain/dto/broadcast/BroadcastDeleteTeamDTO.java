package cn.neorae.wtu.module.team.domain.dto.broadcast;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BroadcastDeleteTeamDTO {


    private String route;

    @Schema(description = "teamId")
    @NotNull(message = "teamId不能为空")
    private Integer teamId;

    @Schema(description = "服务器")
    @NotNull(message = "服务器不能为空")
    private Integer server;
}
