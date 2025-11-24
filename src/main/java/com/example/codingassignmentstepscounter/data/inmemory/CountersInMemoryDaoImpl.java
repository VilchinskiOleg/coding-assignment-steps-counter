package com.example.codingassignmentstepscounter.data.inmemory;

import com.example.codingassignmentstepscounter.data.CountersDao;
import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import com.example.codingassignmentstepscounter.data.TeamsDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountersInMemoryDaoImpl implements CountersDao {

  private final TeamsDao teamsDao;

  @Override
  public boolean createCounter(String teamId, StepsCounter newCounter) {
    return teamsDao.getTeam(teamId)
        .map(team -> {
          team.addCounter(newCounter);
          return true;
        })
        .orElse(false);
  }

  @Override
  public Optional<StepsCounter> incrementCounter(String teamId, String counterId, Integer steps) {
    Optional<StepsCounter> counter = getCounter(teamId, counterId);
    counter.ifPresent(c -> c.incrementValue(steps));
    return counter;
  }

  @Override
  public List<StepsCounter> getAllCounters(String teamId) {
    return teamsDao.getTeam(teamId)
        .map(team -> new ArrayList<>(team.getCounters().values()))
        .orElse(new ArrayList<>());
  }

  @Override
  public boolean deleteCounter(String teamId, String counterId) {
    return teamsDao.getTeam(teamId)
        .map(team -> team.deleteCounter(counterId))
        .orElse(false);
  }

  private Optional<StepsCounter> getCounter(String teamId, String counterId) {
    return teamsDao.getTeam(teamId)
        .map(team -> team.getCounter(counterId));
  }
}
