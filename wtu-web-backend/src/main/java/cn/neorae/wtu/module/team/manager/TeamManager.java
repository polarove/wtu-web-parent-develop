package cn.neorae.wtu.module.team.manager;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.mapper.TeamMapper;
import cn.neorae.wtu.module.team.mapper.TeamMemberMapper;
import cn.neorae.wtu.module.team.mapper.TeamRequirementMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TeamManager {


    @Resource
    private TeamMapper teamMapper;

    @Resource
    private TeamRequirementMapper teamRequirementMapper;

    @Resource
    private TeamMemberMapper teamMemberMapper;

    @Transactional(rollbackFor = Exception.class)
    public void saveTeam(Team team, List<TeamRequirement> teamRequirementList, List<TeamMember> teamMemberList) {
        if (BeanUtil.isNotEmpty(team)){
            teamMapper.insert(team);
        }
        if (ArrayUtil.isNotEmpty(teamRequirementList)){
            teamRequirementMapper.saveBatch(teamRequirementList);
        }
        if (ArrayUtil.isNotEmpty(teamMemberList)){
            teamMemberMapper.saveBatch(teamMemberList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeTeam(Team team, List<Integer> teamRequirementIdList, List<Integer> teamMemberIdList) {
        if (BeanUtil.isNotEmpty(team)){
            teamMapper.insert(team);
        }
        if (ArrayUtil.isNotEmpty(teamRequirementIdList)){
            teamRequirementMapper.removeBatchByIds(teamRequirementIdList);
        }
        if (ArrayUtil.isNotEmpty(teamMemberIdList)){
            teamMemberMapper.removeBatchByIds(teamMemberIdList);
        }
    }
}
