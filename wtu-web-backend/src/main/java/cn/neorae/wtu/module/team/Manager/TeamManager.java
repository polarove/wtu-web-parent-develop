package cn.neorae.wtu.module.team.Manager;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.domain.TeamWarframe;
import cn.neorae.wtu.module.team.mapper.TeamMapper;
import cn.neorae.wtu.module.team.service.TeamMemberService;
import cn.neorae.wtu.module.team.service.TeamRequirementService;
import cn.neorae.wtu.module.team.service.TeamService;
import cn.neorae.wtu.module.team.service.TeamWarframeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TeamManager {


    @Resource
    private TeamService teamService;

    @Resource
    private TeamRequirementService teamRequirementService;

    @Resource
    private TeamMemberService teamMemberService;

    @Transactional(rollbackFor = Exception.class)
    public void saveTeam(Team team, List<TeamRequirement> teamRequirementList, List<TeamMember> teamMemberList) {
        if (BeanUtil.isNotEmpty(team)){
            teamService.save(team);
        }
        if (ArrayUtil.isNotEmpty(teamRequirementList)){
            teamRequirementService.saveBatch(teamRequirementList);
        }
        if (ArrayUtil.isNotEmpty(teamMemberList)){
            teamMemberService.saveBatch(teamMemberList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeTeam(Team team, List<TeamRequirement> teamRequirementList, List<TeamMember> teamMemberList) {
        if (BeanUtil.isNotEmpty(team)){
            teamService.removeById(team);
        }
        if (ArrayUtil.isNotEmpty(teamRequirementList)){
            teamRequirementService.removeBatchByIds(teamRequirementList);
        }
        if (ArrayUtil.isNotEmpty(teamMemberList)){
            teamMemberService.removeBatchByIds(teamMemberList);
        }
    }
}