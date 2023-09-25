package cn.neorae.wtu.module.team.domain.bo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class TeamRequirementsBO {

    private Integer id;

    private String type;

    private String content;

    private Integer isDeleted;
}
