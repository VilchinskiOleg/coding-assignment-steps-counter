package com.example.codingassignmentstepscounter.domain.service;

import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import com.example.codingassignmentstepscounter.domain.model.Team;
import java.util.List;

public interface StepsCounterService {

  List<Team> addTeam(String name);

  Team addCounter(String teamId, String firstName, String lastName);


  StepsCounter incrementCounter(String teamId, String counterId, Integer steps);

  int getTeamTotalSteps(String teamId);

  List<StepsCounter> getCounters(String teamId);

  List<Team> getAllTeams();


  Team deleteCounter(String teamId, String counterId);

  List<Team> deleteTeam(String teamId);
}
