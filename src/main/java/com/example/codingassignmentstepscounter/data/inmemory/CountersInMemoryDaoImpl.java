package com.example.codingassignmentstepscounter.data.inmemory;

import com.example.codingassignmentstepscounter.data.CountersDao;
import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import com.example.codingassignmentstepscounter.data.TeamsDao;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountersInMemoryDaoImpl implements CountersDao {

  private final TeamsDao teamsDao;

  @Override
  public Optional<StepsCounter> getCounter(String teamId, String counterId) {
    return teamsDao.getTeam(teamId)
        .map(team -> team.getCounter(counterId));
  }
}
