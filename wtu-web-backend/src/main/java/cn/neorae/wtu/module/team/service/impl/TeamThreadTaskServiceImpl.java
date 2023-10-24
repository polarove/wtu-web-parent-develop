package cn.neorae.wtu.module.team.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.neorae.wtu.module.team.service.TeamMemberService;
import cn.neorae.wtu.module.team.service.TeamRequirementService;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.domain.bo.TeamBO;
import cn.neorae.wtu.module.team.domain.bo.TeamMemberBO;
import cn.neorae.wtu.module.team.domain.bo.TeamRequirementsBO;
import cn.neorae.wtu.module.team.domain.dto.create.CreateTeamMemberDTO;
import cn.neorae.wtu.module.team.domain.dto.create.CreateTeamRequirementDTO;
import cn.neorae.wtu.module.team.domain.vo.TeamVO;
import cn.neorae.wtu.module.team.mapper.TeamMemberMapper;
import cn.neorae.wtu.module.team.mapper.TeamRequirementMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class TeamThreadTaskServiceImpl {


    @Resource
    private TeamRequirementService teamRequirementService;

    @Resource
    private TeamRequirementMapper teamRequirementMapper;

    @Resource
    private TeamMemberService teamMemberService;

    @Resource
    private TeamMemberMapper teamMemberMapper;

    @Async
    public CompletableFuture<TeamVO> getTeamVO(Team team) {
        TeamVO teamVO = new TeamVO();
        TeamBO teamBO = new TeamBO();
        BeanUtil.copyProperties(team, teamBO);
        teamVO.setTeam(teamBO);
        List<TeamMemberBO> teamMemberBOS = teamMemberMapper
                .selectList(new LambdaQueryWrapper<TeamMember>().eq(TeamMember::getTeamId, team.getId()))
                .parallelStream()
                .map(teamMember -> teamMemberService.getTeamMemberBO(teamMember).join()).toList();
        List<TeamRequirementsBO> teamRequirementsBOS = teamRequirementMapper
                .selectList(new LambdaQueryWrapper<TeamRequirement>().eq(TeamRequirement::getTeamId, team.getId()))
                .parallelStream()
                .map(teamRequirement -> teamRequirementService.getTeamRequirement(teamRequirement).join()).toList();
        teamVO.setMembers(teamMemberBOS);
        teamVO.setRequirements(teamRequirementsBOS);
        return CompletableFuture.completedFuture(teamVO);
    }

    @Async
    public CompletableFuture<TeamRequirement> handleTeamRequirements(
            CreateTeamRequirementDTO teamRequirementDTO, Integer teamId){
        TeamRequirement teamRequirement = new TeamRequirement();
        teamRequirement.setTeamId(teamId);
        BeanUtil.copyProperties(teamRequirementDTO, teamRequirement);
        return CompletableFuture.completedFuture(teamRequirement);
    }

    @Async
    public CompletableFuture<TeamMember> handleTeamMembers(CreateTeamMemberDTO member, Integer teamId){
        TeamMember teamMember = new TeamMember();
        teamMember.setTeamId(teamId);
        teamMember.setUserUuid(member.getUser().getUuid());
        teamMember.setEn(member.getWarframe().getEn());
        teamMember.setCn(member.getWarframe().getCn());
        BeanUtil.copyProperties(member, teamMember);
        return CompletableFuture.completedFuture(teamMember);
    }

    @Async
    public CompletableFuture<Team> setTeamStatus(Team team, Integer status) {
        team.setStatus(status);
        return CompletableFuture.completedFuture(team);
    }
}
