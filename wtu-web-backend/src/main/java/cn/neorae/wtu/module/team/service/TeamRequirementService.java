package cn.neorae.wtu.module.team.service;

import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.domain.bo.TeamRequirementsBO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.concurrent.CompletableFuture;

/**
* @author Neorae
* @description 针对表【team_requirement(新建队伍时的要求表)】的数据库操作Service
* @createDate 2023-09-25 15:07:26
*/
public interface TeamRequirementService extends IService<TeamRequirement> {

    CompletableFuture<TeamRequirementsBO> getTeamRequirement(TeamRequirement teamRequirement);
}
