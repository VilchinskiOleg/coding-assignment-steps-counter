package com.example.codingassignmentstepscounter.data;

import com.example.codingassignmentstepscounter.domain.model.Team;
import java.util.List;
import java.util.Optional;

public interface TeamsDao {

  void createTeam(Team team);
  Optional<Team> getTeam(String teamId);

  List<Team> getTeams();

  boolean deleteTeam(String teamId);
}
