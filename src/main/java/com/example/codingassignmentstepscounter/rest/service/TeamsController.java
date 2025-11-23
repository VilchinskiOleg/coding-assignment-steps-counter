package com.example.codingassignmentstepscounter.rest.service;

import com.example.codingassignmentstepscounter.domain.service.TeamService;
import com.example.codingassignmentstepscounter.mapper.TeamMapper;
import com.example.codingassignmentstepscounter.rest.model.Team;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Team Management", description = "APIs for managing Teams")
public class TeamsController {

  private final TeamService teamService;
  private final TeamMapper teamMapper;

  @Operation(summary = "Create Team", description = "Creates new team")
  @PostMapping("/teams")
  public List<Team> createTeam(@RequestParam String name) {
    teamService.addTeam(name);
    return teamMapper.toDtoList(teamService.getAllTeams());
  }

  @Operation(summary = "Get Total Steps", description = "Returns total steps for team")
  @GetMapping("/teams/{teamId}/total")
  public int getTeamTotalSteps(@PathVariable String teamId) {
    return teamService.getTeamTotalSteps(teamId);
  }

  @Operation(summary = "Get Teams", description = "Lists data for all teams")
  @GetMapping("/teams")
  public List<Team> getTeams() {
    return teamMapper.toDtoList(teamService.getAllTeams());
  }

  @Operation(summary = "Delete Team", description = "Removes chosen team")
  @DeleteMapping("/teams/{teamId}")
  public List<Team> deleteTeam(@PathVariable String teamId) {
    teamService.deleteTeam(teamId);
    return teamMapper.toDtoList(teamService.getAllTeams());
  }
}
