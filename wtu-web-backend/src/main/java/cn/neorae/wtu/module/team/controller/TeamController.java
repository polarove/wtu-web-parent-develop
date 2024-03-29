package cn.neorae.wtu.module.team.controller;


import cn.neorae.common.annotation.FreePass;
import cn.neorae.common.response.ResponseVO;
import cn.neorae.wtu.module.team.domain.dto.broadcast.BroadcastDeleteTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.broadcast.BroadcastTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.broadcast.BroadcastToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.dto.create.CreateTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.get.GetTeamDTO;
import cn.neorae.wtu.module.team.domain.dto.join.ApplicationDTO;
import cn.neorae.wtu.module.team.domain.dto.toggle.ToggleTeamStatusDTO;
import cn.neorae.wtu.module.team.domain.vo.TeamVO;
import cn.neorae.wtu.module.team.service.TeamService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@Tag(name = "队伍")
@Slf4j
public class TeamController {

    @Resource
    private TeamService teamService;

    @PostMapping("/createTeam")
    public ResponseVO<Integer> createTeam(@Valid @RequestBody CreateTeamDTO createTeamDTO) {
        return teamService.createTeam(createTeamDTO);
    }

    @PostMapping("/broadcastTeam")
    public ResponseVO<String> broadcastTeam(@Valid @RequestBody BroadcastTeamDTO broadcastTeamDTO) {
        return teamService.broadcastTeam(broadcastTeamDTO);
    }

    @PostMapping("/broadcastDeleteTeam")
    public ResponseVO<String> broadcastDeleteTeam(@Valid @RequestBody BroadcastDeleteTeamDTO broadcastDeleteTeamDTO) {
        return teamService.broadcastDeleteTeam(broadcastDeleteTeamDTO);
    }

    @PostMapping("/broadcastToggleTeamStatus")
    public ResponseVO<String> broadcastToggleTeamStatus(@Valid @RequestBody BroadcastToggleTeamStatusDTO broadcastToggleTeamStatusDTO) {
        return teamService.broadcastToggleTeamStatus(broadcastToggleTeamStatusDTO);
    }

    @GetMapping("/getTeamById")
    public ResponseVO<TeamVO> getTeamById(@Valid @RequestParam Integer teamId) {
        return teamService.getTeamById(teamId);
    }

    @PostMapping("/getTeamList")
    @FreePass
    public ResponseVO<IPage<TeamVO>> getTeamList(@Valid @RequestBody GetTeamDTO getTeamDTO) {
        return teamService.getTeamList(getTeamDTO);
    }

    @PostMapping("/toggleTeamStatus")
    public ResponseVO<String> toggleTeamStatus(@Valid @RequestBody ToggleTeamStatusDTO toggleTeamStatusDTO) {
        return teamService.toggleTeamStatus(toggleTeamStatusDTO);
    }

    @PostMapping("/removeTeam")
    public ResponseVO<String> removeTeam(@Valid @RequestBody Integer teamId) {
        return teamService.removeTeamById(teamId);
    }

    // todo: 2021/10/3 未完成
    @GetMapping("/getJoinTeamRequestListByUserId")
    public ResponseVO<List<TeamVO>> getJoinTeamRequestListByUserId(@Valid @RequestParam Integer userId) {
        return teamService.getJoinTeamRequestListByUserId(userId);
    }

    @PostMapping("/joinTeam")
    public ResponseVO<String> joinTeam(@Valid @RequestBody ApplicationDTO applicationDTO) {
        String receiver = applicationDTO.getReceiver().getUuid();
        return teamService.applicationResult(applicationDTO,receiver);
    }

    @PostMapping("/joinTeam/result")
    public ResponseVO<String> applicationResult(@Valid @RequestBody ApplicationDTO applicationDTO) {
        String from = applicationDTO.getFrom().getUuid();
        return teamService.applicationResult(applicationDTO,from);
    }
}
