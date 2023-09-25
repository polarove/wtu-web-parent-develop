package cn.neorae.wtu.module.team.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ArrayUtil;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.manager.TeamManager;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.domain.TeamRequirement;
import cn.neorae.wtu.module.team.domain.TeamWarframe;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamDTO;
import cn.neorae.wtu.module.team.mapper.TeamWarframeMapper;
import cn.neorae.wtu.module.team.service.TeamMemberService;
import cn.neorae.wtu.module.team.service.TeamRequirementService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.service.TeamService;
import cn.neorae.wtu.module.team.mapper.TeamMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author Neorae
* @description 针对表【team(队伍表)】的数据库操作Service实现
* @createDate 2023-09-25 08:50:11
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService{


    @Resource
    private TeamWarframeMapper teamWarframeMapper;

    @Resource
    private TeamRequirementService teamRequirementService;

    @Resource
    private TeamMemberService teamMemberService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<String> createTeam(CreateTeamDTO createTeamDTO) {

        Team team = new Team();
        team.setTitle(createTeamDTO.getTitle());
        team.setServer(createTeamDTO.getServer());
        team.setCreatorUuid(StpUtil.getLoginIdAsString());
        team.setChannel(createTeamDTO.getChannel());
        team.setUuid(UUID.randomUUID().toString());

        List<TeamRequirement> requirementList = createTeamDTO.getRequirements().stream().map(requirement -> {
            TeamRequirement teamRequirement = new TeamRequirement();
            teamRequirement.setTeamUuid(team.getUuid());
            BeanUtil.copyProperties(requirement, teamRequirement);
            return teamRequirement;
        }).toList();

        List<TeamMember> memberList = createTeamDTO.getMembers().stream().map(member -> {
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamUuid(team.getUuid());
            teamMember.setFocus(member.getFocus());

            Integer warframeId;
            TeamWarframe teamWarframe = teamWarframeMapper.selectOne(new LambdaQueryWrapper<TeamWarframe>().eq(TeamWarframe::getEn, member.getWarframe().getEn()));
            if (!BeanUtil.isNotEmpty(teamWarframe)) {
                teamWarframe = new TeamWarframe();
                teamWarframe.setEn(member.getWarframe().getEn());
                teamWarframe.setCn(member.getWarframe().getCn());
                teamWarframeMapper.insert(teamWarframe);
            }
            warframeId = teamWarframe.getId();
            teamMember.setWarframeId(warframeId);

            teamMember.setRole(member.getRole());
            teamMember.setUserUuid(member.getUser().getUuid());

            return teamMember;
        }).toList();

        // 保存队伍
        if (BeanUtil.isNotEmpty(team)){
            save(team);
            updateById(team);
        }
        if (ArrayUtil.isNotEmpty(requirementList)){
            teamRequirementService.saveBatch(requirementList);
        }
        if (ArrayUtil.isNotEmpty(memberList)){
            teamMemberService.saveBatch(memberList);
        }
        return ResponseVO.completed();
    }

    @Override
    @Transactional
    public ResponseVO<String> removeTeam(String teamUuid) {
        Team team = this.getOne(new LambdaQueryWrapper<Team>().eq(Team::getUuid, teamUuid));

        List<Integer> requirementIdList = teamRequirementService
                .list(new LambdaQueryWrapper<TeamRequirement>().eq(TeamRequirement::getTeamUuid, teamUuid))
                .stream()
                .peek(teamRequirement -> {
                }).map(TeamRequirement::getId).toList();

        List<Integer> memberIdList = teamMemberService
                .list(new LambdaQueryWrapper<TeamMember>().eq(TeamMember::getTeamUuid, teamUuid))
                .stream()
                .peek(teamRequirement -> {
                }).map(TeamMember::getId).toList();


        if (BeanUtil.isNotEmpty(team)){
            removeById(team);
        }
        if (ArrayUtil.isNotEmpty(requirementIdList)){
            teamRequirementService.removeBatchByIds(requirementIdList);
        }
        if (ArrayUtil.isNotEmpty(memberIdList)){
            teamMemberService.removeBatchByIds(memberIdList);
        }
        return ResponseVO.completed();
    }
}





