package cn.neorae.wtu.module.team.mapper;

import cn.neorae.wtu.module.team.domain.TeamRequirement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Neorae
* @description 针对表【team_requirement(新建队伍时的要求表)】的数据库操作Mapper
* @createDate 2023-09-25 08:50:18
* @Entity cn.neorae.wtu.module.team.domain.TeamRequirement
*/
public interface TeamRequirementMapper extends BaseMapper<TeamRequirement> {

    void saveBatch(List<TeamRequirement> teamRequirementList);

    void removeBatchByIds(List<Integer> teamRequirementIdList);
}




