package com.example.codingassignmentstepscounter.data.inmemory;

import com.example.codingassignmentstepscounter.domain.model.Team;
import com.example.codingassignmentstepscounter.data.TeamsDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class TeamsInMemoryDaoImpl implements TeamsDao {

  private final ConcurrentHashMap<String, Team> teams = new ConcurrentHashMap<>();

  @Override
  public void createTeam(Team team) {
    teams.put(team.getId(), team);
  }

  @Override
  public Optional<Team> getTeam(String teamId) {
    return Optional.ofNullable(teams.get(teamId));
  }

  @Override
  public List<Team> getTeams() {
    return new ArrayList<>(teams.values());
  }

  @Override
  public boolean deleteTeam(String teamId) {
    return teams.remove(teamId) != null;
  }
}
