package com.example.codingassignmentstepscounter.domain.service;

import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import java.util.List;

public interface CounterService {

  void addCounter(String teamId, String firstName, String lastName);

  StepsCounter incrementCounter(String teamId, String counterId, Integer steps);

  List<StepsCounter> getCounters(String teamId);

  void deleteCounter(String teamId, String counterId);
}
