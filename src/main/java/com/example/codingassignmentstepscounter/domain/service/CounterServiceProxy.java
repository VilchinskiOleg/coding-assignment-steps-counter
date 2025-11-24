package com.example.codingassignmentstepscounter.domain.service;

import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import com.example.codingassignmentstepscounter.utils.LockUtils;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("synchronizedCounterService")
public class CounterServiceProxy implements CounterService {

  private final CounterService counterService;
  private final ConcurrentHashMap<String, ReentrantReadWriteLock> lockPerTeamMap;

  public CounterServiceProxy(@Qualifier("counterService") CounterService counterService,
      ConcurrentHashMap<String, ReentrantReadWriteLock> lockPerTeamMap) {
    this.counterService = counterService;
    this.lockPerTeamMap = lockPerTeamMap;
  }

  @Override
  public void addCounter(String teamId, String firstName, String lastName) {
    Optional<ReentrantReadWriteLock.WriteLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::writeLock);

    LockUtils.executeWithLock(lock, () -> counterService.addCounter(teamId, firstName, lastName));
  }

  @Override
  public StepsCounter incrementCounter(String teamId, String counterId, Integer steps) {
    Optional<ReentrantReadWriteLock.ReadLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::readLock);

    // Use ReadLock here because StepsCounter::incrementValue() already synchronized
    return LockUtils.executeWithLock(lock,
        () -> counterService.incrementCounter(teamId, counterId, steps));
  }

  @Override
  public List<StepsCounter> getCounters(String teamId) {
    Optional<ReentrantReadWriteLock.ReadLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::readLock);

    return LockUtils.executeWithLock(lock, () -> counterService.getCounters(teamId));
  }

  @Override
  public void deleteCounter(String teamId, String counterId) {
    Optional<ReentrantReadWriteLock.WriteLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::writeLock);

    LockUtils.executeWithLock(lock, () -> counterService.deleteCounter(teamId, counterId));
  }
}
