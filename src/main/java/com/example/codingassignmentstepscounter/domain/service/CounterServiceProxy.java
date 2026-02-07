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

    LockUtils.executeWithWriteLock(lock, () -> counterService.addCounter(teamId, firstName, lastName));
  }

  @Override
  public StepsCounter incrementCounter(String teamId, String counterId, Integer steps) {
    // Используем WriteLock, так как incrementCounter изменяет состояние (даже если incrementValue синхронизирован,
    // нам нужно защитить операцию получения counter из коллекции и его модификацию как единое целое)
    Optional<ReentrantReadWriteLock.WriteLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::writeLock);

    return LockUtils.executeWithWriteLock(lock,
        () -> counterService.incrementCounter(teamId, counterId, steps));
  }

  @Override
  public List<StepsCounter> getCounters(String teamId) {
    Optional<ReentrantReadWriteLock.ReadLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::readLock);

    return LockUtils.executeWithReadLock(lock, () -> counterService.getCounters(teamId));
  }

  @Override
  public void deleteCounter(String teamId, String counterId) {
    Optional<ReentrantReadWriteLock.WriteLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::writeLock);

    LockUtils.executeWithWriteLock(lock, () -> counterService.deleteCounter(teamId, counterId));
  }
}
