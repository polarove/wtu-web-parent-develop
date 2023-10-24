package cn.neorae.wtu.module.team.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.neorae.wtu.module.team.service.TeamRequirementService;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.domain.bo.TeamRequirementsBO;
import cn.neorae.wtu.module.team.mapper.TeamRequirementMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
* @author Neorae
* @description 针对表【team_requirement(新建队伍时的要求表)】的数据库操作Service实现
* @createDate 2023-09-25 15:07:26
*/
@Service
public class TeamRequirementServiceImpl extends ServiceImpl<TeamRequirementMapper, TeamRequirement>
    implements TeamRequirementService {

    @Override
    @Async
    public CompletableFuture<TeamRequirementsBO> getTeamRequirement(TeamRequirement teamRequirement) {
        TeamRequirementsBO teamRequirementsBO = new TeamRequirementsBO();
        BeanUtil.copyProperties(teamRequirement, teamRequirementsBO);
        return CompletableFuture.completedFuture(teamRequirementsBO);
    }


}




