package com.example.codingassignmentstepscounter.domain.service;

import com.example.codingassignmentstepscounter.data.TeamsDao;
import com.example.codingassignmentstepscounter.domain.model.Team;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

  private static final String TEAM_IS_NOT_PRESENT_TMP = "Team with ID = %s is not present";

  private final TeamsDao teamsDao;

  @Override
  public void addTeam(String name) {
    Team newTeam = createTeam(name);
    teamsDao.createTeam(newTeam);
    log.info("New team with ID = {} created", newTeam.getId());
  }

  @Override
  public Team getTeamRequired(String teamId) {
    return teamsDao.getTeam(teamId)
        .orElseThrow(
            () -> new NoSuchElementException(String.format(TEAM_IS_NOT_PRESENT_TMP, teamId)));
  }

  @Override
  public List<Team> getAllTeams() {
    return teamsDao.getTeams();
  }

  @Override
  public int getTeamTotalSteps(String teamId) {
    return getTeamRequired(teamId).getTotalSteps();
  }

  @Override
  public void deleteTeam(String teamId) {
    if (teamsDao.deleteTeam(teamId)) {
      log.info("Team with ID = {} was deleted", teamId);
    } else {
      throw new NoSuchElementException(String.format(TEAM_IS_NOT_PRESENT_TMP, teamId));
    }
  }


  private Team createTeam(String name) {
    Team team = new Team(UUID.randomUUID().toString());
    team.setName(name);
    return team;
  }
}
