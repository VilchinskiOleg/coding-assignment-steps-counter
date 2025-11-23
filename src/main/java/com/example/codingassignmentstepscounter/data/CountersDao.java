package com.example.codingassignmentstepscounter.data;

import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import com.example.codingassignmentstepscounter.domain.model.Team;
import java.util.List;
import java.util.Optional;

public interface CountersDao {

  boolean createCounter(String teamId, StepsCounter newCounter);

  Optional<StepsCounter> incrementCounter(String teamId, String counterId, Integer steps);

  List<StepsCounter> getAllCounters(String teamId);

  boolean deleteCounter(String teamId, String counterId);
}
