package cn.neorae.wtu.module.team.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.neorae.wtu.module.team.domain.TeamMember;
import cn.neorae.wtu.module.team.service.TeamMemberService;
import cn.neorae.wtu.module.team.mapper.TeamMemberMapper;
import org.springframework.stereotype.Service;

/**
* @author Neorae
* @description 针对表【team_member(创建队伍时的队员信息)】的数据库操作Service实现
* @createDate 2023-09-25 08:50:15
*/
@Service
public class TeamMemberServiceImpl extends ServiceImpl<TeamMemberMapper, TeamMember>
    implements TeamMemberService{

}




