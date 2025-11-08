package com.example.codingassignmentstepscounter.domain.service;

import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import com.example.codingassignmentstepscounter.domain.model.Team;
import com.example.codingassignmentstepscounter.data.CountersDao;
import com.example.codingassignmentstepscounter.data.TeamsDao;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StepsCounterServiceImpl implements StepsCounterService {

  private static final String TEAM_IS_NOT_PRESENT_TMP = "Team with ID = %s is not present";
  private static final String COUNTER_IS_NOT_PRESENT_TMP = "Counter for Team = %s and with ID = %s is not present";

  private final TeamsDao teamsDao;
  private final CountersDao countersDao;

  @Override
  public List<Team> addTeam(String name) {
    Team team = createTeam(name);
    teamsDao.createTeam(team);
    return teamsDao.getTeams();
  }

  @Override
  public Team addCounter(String teamId, String firstName, String lastName) {
    Team team = getTeamRequired(teamId);
    StepsCounter newCounter = createCounter(firstName, lastName);
    team.addCounter(newCounter);
    return team;
  }

  @Override
  public StepsCounter incrementCounter(String teamId, String counterId, Integer steps) {
    StepsCounter counter = getCounterRequired(teamId, counterId);
    counter.incrementValue(steps);
    return counter;
  }

  @Override
  public int getTeamTotalSteps(String teamId) {
    return getTeamRequired(teamId).getTotalSteps();
  }

  @Override
  public List<StepsCounter> getCounters(String teamId) {
    Team team = getTeamRequired(teamId);
    return new ArrayList<>(team.getCounters().values());
  }

  @Override
  public List<Team> getAllTeams() {
    return teamsDao.getTeams();
  }

  @Override
  public Team deleteCounter(String teamId, String counterId) {
    Team team = getTeamRequired(teamId);
    if (team.deleteCounter(counterId)) {
      return team;
    } else {
      throw new NoSuchElementException(
          String.format(COUNTER_IS_NOT_PRESENT_TMP, teamId, counterId));
    }
  }

  @Override
  public List<Team> deleteTeam(String teamId) {
    if (teamsDao.deleteTeam(teamId)) {
      return teamsDao.getTeams();
    } else {
      throw new NoSuchElementException(String.format(TEAM_IS_NOT_PRESENT_TMP, teamId));
    }
  }


  private Team createTeam(String name) {
    Team team = new Team(UUID.randomUUID().toString());
    team.setName(name);
    return team;
  }

  private StepsCounter createCounter(String firstName, String lastName) {
    StepsCounter counter = new StepsCounter(UUID.randomUUID().toString());
    counter.setMemberFirstName(firstName);
    counter.setMemberLastName(lastName);
    return counter;
  }

  private Team getTeamRequired(String teamId) {
    return teamsDao.getTeam(teamId)
        .orElseThrow(
            () -> new NoSuchElementException(String.format(TEAM_IS_NOT_PRESENT_TMP, teamId)));
  }

  private StepsCounter getCounterRequired(String teamId, String counterId) {
    return countersDao.getCounter(teamId, counterId)
        .orElseThrow(() -> new NoSuchElementException(
            String.format(COUNTER_IS_NOT_PRESENT_TMP, teamId, counterId)));
  }
}
