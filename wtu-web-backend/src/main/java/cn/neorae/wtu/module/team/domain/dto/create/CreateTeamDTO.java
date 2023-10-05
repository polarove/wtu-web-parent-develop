package cn.neorae.wtu.module.team.domain.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateTeamDTO {

    private String title;

    private List<CreateTeamRequirementDTO> requirements;

    private List<CreateTeamMemberDTO> members;

    @Schema(description = "队伍类型")
    @NotNull(message = "队伍类型不能为空")
    private Boolean isPublic;

    @Schema(description = "所处服务器")
    @NotNull(message = "所处服务器不能为空")
    private Integer server;

    @Schema(description = "所处频道")
    @NotBlank(message = "所处频道不能为空")
    private String channel;

}
