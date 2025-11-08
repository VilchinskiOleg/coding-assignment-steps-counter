package com.example.codingassignmentstepscounter.domain.model;

import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {

  private final String id;
  private String name;
  private ConcurrentHashMap<String, StepsCounter> counters = new ConcurrentHashMap<>();

  public Team(String id) {
    this.id = id;
  }

  public int getTotalSteps() {
    return counters.values().stream()
        .mapToInt(StepsCounter::getValue)
        .sum();
  }

  public void addCounter(StepsCounter stepsCounter) {
    counters.put(stepsCounter.getId(), stepsCounter);
  }

  public StepsCounter getCounter(String counterId) {
    return counters.get(counterId);
  }

  public boolean deleteCounter(String counterId) {
    return counters.remove(counterId) != null;
  }
}
