package com.example.codingassignmentstepscounter.domain.service;

import com.example.codingassignmentstepscounter.domain.model.Team;
import java.util.List;

public interface TeamService {

  Team addTeam(String name);

  Team getTeamRequired(String teamId);

  List<Team> getAllTeams();

  Integer getTeamTotalSteps(String teamId);

  void deleteTeam(String teamId);
}
