package cn.neorae.wtu.module.team.domain.dto.toggle;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ToggleTeamStatusDTO {




    @NotBlank(message = "id不能为空")
    private Integer teamId;

    private Integer status;
}
