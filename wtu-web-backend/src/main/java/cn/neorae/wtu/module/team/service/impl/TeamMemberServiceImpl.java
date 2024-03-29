package cn.neorae.wtu.module.team.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.wtu.module.account.domain.User;
import cn.neorae.wtu.module.account.domain.bo.UserBoosterBO;
import cn.neorae.wtu.module.account.mapper.UserMapper;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.domain.bo.TeamMemberBO;
import cn.neorae.wtu.module.team.domain.bo.TeamUserBO;
import cn.neorae.wtu.module.team.domain.bo.TeamWarframeBO;
import cn.neorae.wtu.module.team.mapper.TeamMemberMapper;
import cn.neorae.wtu.module.team.service.TeamMemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
* @author Neorae
* @description 针对表【team_member(创建队伍时的队员信息)】的数据库操作Service实现
* @createDate 2023-09-25 15:06:50
*/
@Service
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember>
    implements TeamMemberService {

    @Resource
    private UserMapper userMapper;

    @Async
    @Override
    public CompletableFuture<TeamMemberBO> getTeamMemberBO(TeamMember member) {
        TeamMemberBO teamMemberBO = new TeamMemberBO();
        BeanUtil.copyProperties(member, teamMemberBO);
        TeamWarframeBO teamWarframeBO = new TeamWarframeBO();
        teamWarframeBO.setEn(member.getEn());
        teamWarframeBO.setCn(member.getCn());
        teamMemberBO.setWarframe(teamWarframeBO);
        TeamUserBO teamUserBO = new TeamUserBO();
        if (StrUtil.isNotBlank(member.getUserUuid())){
            User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUuid, member.getUserUuid()));
            UserBoosterBO userBoosterBO = new UserBoosterBO();
            BeanUtil.copyProperties(user, userBoosterBO);
            BeanUtil.copyProperties(user, teamUserBO);
            teamUserBO.setBooster(userBoosterBO);
        }
        teamMemberBO.setUser(teamUserBO);
        return CompletableFuture.completedFuture(teamMemberBO);
    }
}




