package cn.neorae.wtu.module.team.service;

import cn.neorae.wtu.module.team.domain.Team;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.domain.bo.TeamBO;
import cn.neorae.wtu.module.team.domain.bo.TeamMemberBO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
* @author Neorae
* @description 针对表【team_member(创建队伍时的队员信息)】的数据库操作Service
* @createDate 2023-09-25 15:06:50
*/
public interface TeamMemberService extends IService<TeamMember> {

    CompletableFuture<List<TeamMemberBO>> getTeamMemberBOList(Team team);
}
