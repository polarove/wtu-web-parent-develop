package cn.neorae.wtu.module.team.domain.bo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class TeamMemberBO {

    private Integer id;

    private String focus;

    private Integer role;

    private TeamWarframeBO teamWarframeBO;

    private Integer isDeleted;
}
