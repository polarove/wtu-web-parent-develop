package cn.neorae.wtu.module.team.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.bo.TeamRequirementsBO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.service.TeamRequirementService;
import cn.neorae.wtu.module.team.mapper.TeamRequirementMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author Neorae
* @description 针对表【team_requirement(新建队伍时的要求表)】的数据库操作Service实现
* @createDate 2023-09-25 15:07:26
*/
@Service
public class TeamRequirementServiceImpl extends ServiceImpl<TeamRequirementMapper, TeamRequirement>
    implements TeamRequirementService{

    @Override
    @Async
    public CompletableFuture<List<TeamRequirementsBO>> getTeamRequirementList(Team team) {
        List<TeamRequirementsBO> teamRequirementList = this.baseMapper
                .selectList(new LambdaQueryWrapper<TeamRequirement>().eq(TeamRequirement::getTeamId, team.getId()))
                        .stream().map(requirement -> {
                    TeamRequirementsBO teamRequirementsBO = new TeamRequirementsBO();
                    BeanUtil.copyProperties(requirement, teamRequirementsBO);
                    return teamRequirementsBO;
                }).collect(Collectors.toList());
        return CompletableFuture.completedFuture(teamRequirementList);
    }
}




