package cn.neorae.wtu.module.team.domain.dto.join;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationReceiverDTO {

    @Schema(description = "uuid")
    @NotBlank(message = "uuid不能为空")
    private String uuid;

    private String name;

    private String avatar;
}
