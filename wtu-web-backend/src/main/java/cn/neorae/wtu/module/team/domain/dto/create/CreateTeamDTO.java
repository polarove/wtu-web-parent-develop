package cn.neorae.wtu.module.team.domain.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateTeamDTO {

    private String title;

    private List<CreateTeamRequirementDTO> requirements;

    private List<CreateTeamMemberDTO> members;

    @NotNull(message = "所处服务器不能为空")
    private Integer server;

    @NotBlank(message = "所处频道不能为空")
    private String channel;

}
