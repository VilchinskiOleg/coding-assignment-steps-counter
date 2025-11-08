package com.example.codingassignmentstepscounter.rest.service;

import com.example.codingassignmentstepscounter.domain.service.StepsCounterService;
import com.example.codingassignmentstepscounter.mapper.TeamMapper;
import com.example.codingassignmentstepscounter.rest.model.Team;
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
public class TeamsController {

  private final StepsCounterService stepsCounterService;
  private final TeamMapper teamMapper;

  @PostMapping("/teams")
  public List<Team> createTeam(@RequestParam String name) {
    var updatedTeams = stepsCounterService.addTeam(name);
    return teamMapper.toDtoList(updatedTeams);
  }

  @GetMapping("/teams/{teamId}/total")
  public int getTeamTotalSteps(@PathVariable String teamId) {
    return stepsCounterService.getTeamTotalSteps(teamId);
  }

  @GetMapping("/teams")
  public List<Team> getTeams() {
    var teams = stepsCounterService.getAllTeams();
    return teamMapper.toDtoList(teams);
  }

  @DeleteMapping("/teams/{teamId}")
  public List<Team> deleteTeam(@PathVariable String teamId) {
    var updatedTeams = stepsCounterService.deleteTeam(teamId);
    return teamMapper.toDtoList(updatedTeams);
  }
}
