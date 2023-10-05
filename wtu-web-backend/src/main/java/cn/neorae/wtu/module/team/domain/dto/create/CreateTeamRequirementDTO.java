package cn.neorae.wtu.module.team.domain.dto.create;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTeamRequirementDTO {


    @Schema(description = "类型")
    @NotBlank(message = "类型不能为空")
    private String type;

    @Schema(description = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;
}
