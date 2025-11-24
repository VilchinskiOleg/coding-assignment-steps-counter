package com.example.codingassignmentstepscounter.domain.service;

import static com.example.codingassignmentstepscounter.domain.service.TeamServiceImpl.TEAM_IS_NOT_PRESENT_TMP;

import com.example.codingassignmentstepscounter.data.CountersDao;
import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Qualifier("counterService")
@RequiredArgsConstructor
public class CounterServiceImpl implements CounterService {

  private static final String COUNTER_IS_NOT_PRESENT_TMP
      = "Counter for Team = %s and with ID = %s is not present";


  private final CountersDao countersDao;

  @Override
  public void addCounter(String teamId, String firstName, String lastName) {
    StepsCounter newCounter = createCounter(firstName, lastName);
    if (countersDao.createCounter(teamId, newCounter)) {
      log.info("New counter with ID = {} created", newCounter.getId());
    } else {
      throw new NoSuchElementException(String.format(TEAM_IS_NOT_PRESENT_TMP, teamId));
    }
  }

  @Override
  public StepsCounter incrementCounter(String teamId, String counterId, Integer steps) {
    Optional<StepsCounter> updatedCounter = countersDao.incrementCounter(teamId, counterId, steps);
    if (updatedCounter.isEmpty()) {
      throw new NoSuchElementException(String.format(COUNTER_IS_NOT_PRESENT_TMP, teamId, counterId));
    }
    int updatedStepsCount = updatedCounter.get().getValue();
    log.info("Updated counter = {} from {} to {}",
        updatedCounter.get().getId(), updatedStepsCount - steps, updatedStepsCount);
    return updatedCounter.get();
  }

  @Override
  public List<StepsCounter> getCounters(String teamId) {
    return countersDao.getAllCounters(teamId);
  }

  @Override
  public void deleteCounter(String teamId, String counterId) {
    if (countersDao.deleteCounter(teamId, counterId)) {
      log.info("Counter with ID = {} was deleted", counterId);
    } else {
      throw new NoSuchElementException(String.format(COUNTER_IS_NOT_PRESENT_TMP, teamId, counterId));
    }
  }


  private StepsCounter createCounter(String firstName, String lastName) {
    StepsCounter counter = new StepsCounter(UUID.randomUUID().toString());
    counter.setMemberFirstName(firstName);
    counter.setMemberLastName(lastName);
    return counter;
  }
}
