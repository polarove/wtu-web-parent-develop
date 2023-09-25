package cn.neorae.wtu.module.team.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.bo.TeamBO;
import cn.neorae.wtu.module.team.domain.bo.TeamMemberBO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.service.TeamMemberService;
import cn.neorae.wtu.module.team.mapper.TeamMemberMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author Neorae
* @description 针对表【team_member(创建队伍时的队员信息)】的数据库操作Service实现
* @createDate 2023-09-25 15:06:50
*/
@Service
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember>
    implements TeamMemberService{


    @Async
    @Override
    public CompletableFuture<List<TeamMemberBO>> getTeamMemberBOList(Team team) {
        List<TeamMemberBO> teamMemberList =  this.baseMapper
                .selectList(new LambdaQueryWrapper<TeamMember>().eq(TeamMember::getTeamId, team.getId()))
                .stream().map(member -> {
                    TeamMemberBO teamMemberBO = new TeamMemberBO();
                    BeanUtil.copyProperties(member, teamMemberBO);
                    return teamMemberBO;
                }).collect(Collectors.toList());
        return CompletableFuture.completedFuture(teamMemberList);
    }
}




