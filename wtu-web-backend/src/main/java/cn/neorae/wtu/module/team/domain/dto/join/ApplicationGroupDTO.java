package cn.neorae.wtu.module.team.domain.dto.join;

import cn.neorae.wtu.module.account.domain.bo.UserBoosterBO;
import lombok.Data;

import java.util.List;

@Data
public class ApplicationGroupDTO {

    private String uuid;

    private String title;

    private UserBoosterBO booster;

    private int[][] matrix;

    private List<ApplicationDTO> applications;
}
