package cn.neorae.wtu.module.team.domain.dto.toggle;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ToggleTeamStatusDTO {




    @NotNull(message = "id不能为空")
    private Integer teamId;

    @NotNull(message = "id不能为空")
    private Integer status;
}
