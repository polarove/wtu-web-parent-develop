package cn.neorae.wtu.module.team.controller;


import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.dto.CreateTeamDTO;
import cn.neorae.wtu.module.team.service.TeamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
@Tag(name = "队伍接口")
public class Team {

    @Resource
    private TeamService teamService;

    @PostMapping("/createTeam")
    public ResponseVO<String> createTeam(@RequestBody CreateTeamDTO createTeamDTO) {

        return teamService.createTeam(createTeamDTO);
    }
}
