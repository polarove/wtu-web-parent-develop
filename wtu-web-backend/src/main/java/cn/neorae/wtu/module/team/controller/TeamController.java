package cn.neorae.wtu.module.team.controller;


import cn.neorae.common.annotation.FreePass;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.dto.remove.BroadcastDeleteTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.create.BroadcastTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.create.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.get.GetTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.join.JoinTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.toggle.BroadcastToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.dto.toggle.ToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.vo.TeamVO;
import cn.neorae.wtu.module.team.service.TeamService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@Tag(name = "队伍接口")
@Slf4j
public class TeamController {

    @Resource
    private TeamService teamService;

    @PostMapping("/createTeam")
    public ResponseVO<Integer> createTeam(@RequestBody CreateTeamDTO createTeamDTO) {
        return teamService.createTeam(createTeamDTO);
    }

    @PostMapping("/broadcastTeam")
    public ResponseVO<String> broadcastTeam(@RequestBody BroadcastTeamDTO broadcastTeamDTO) {
        return teamService.broadcastTeam(broadcastTeamDTO);
    }

    @PostMapping("/broadcastDeleteTeam")
    public ResponseVO<String> broadcastDeleteTeam(@RequestBody BroadcastDeleteTeamDTO broadcastDeleteTeamDTO) {
        return teamService.broadcastDeleteTeam(broadcastDeleteTeamDTO);
    }

    @PostMapping("/broadcastToggleTeamStatus")
    public ResponseVO<String> broadcastToggleTeamStatus(@RequestBody BroadcastToggleTeamStatusDTO broadcastToggleTeamStatusDTO) {
        return teamService.broadcastToggleTeamStatus(broadcastToggleTeamStatusDTO);
    }

    @GetMapping("/getTeamById")
    public ResponseVO<TeamVO> getTeamById(@RequestParam Integer teamId) {
        return teamService.getTeamById(teamId);
    }

    @PostMapping("/getTeamList")
    @FreePass
    public ResponseVO<IPage<TeamVO>> getTeamList(@RequestBody GetTeamDTO getTeamDTO) {
        return teamService.getTeamList(getTeamDTO);
    }

    @PostMapping("/toggleTeamStatus")
    public ResponseVO<String> toggleTeamStatus(@RequestBody ToggleTeamStatusDTO toggleTeamStatusDTO) {
        return teamService.toggleTeamStatus(toggleTeamStatusDTO);
    }

    @GetMapping("/removeTeam")
    public ResponseVO<String> removeTeam(@RequestParam Integer teamId) {
        return teamService.removeTeamById(teamId);
    }

    @PostMapping("/joinTeam")
    public ResponseVO<String> joinTeam(@RequestBody JoinTeamDTO joinTeamDTO) {
        return teamService.joinTeam(joinTeamDTO);
    }

}
