package com.example.codingassignmentstepscounter.domain.service;

import com.example.codingassignmentstepscounter.domain.model.Team;
import com.example.codingassignmentstepscounter.utils.LockUtils;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("synchronizedTeamService")
public class TeamServiceProxy implements TeamService {

  private final TeamService teamService;
  private final ConcurrentHashMap<String, ReentrantReadWriteLock> lockPerTeamMap;

  public TeamServiceProxy(@Qualifier("teamService") TeamService teamService,
      ConcurrentHashMap<String, ReentrantReadWriteLock> lockPerTeamMap) {
    this.teamService = teamService;
    this.lockPerTeamMap = lockPerTeamMap;
  }

  @Override
  public Team addTeam(String name) {
    Team newTeam = teamService.addTeam(name);
    // Здесь достаточно простого put, так как команда только что создана и конфликта быть не может
    lockPerTeamMap.put(newTeam.getId(), new ReentrantReadWriteLock());
    return newTeam;
  }

  @Override
  public Team getTeamRequired(String teamId) {
    Optional<ReadLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::readLock);

    return LockUtils.executeWithReadLock(lock, () -> teamService.getTeamRequired(teamId));
  }

  @Override
  public List<Team> getAllTeams() {
    return teamService.getAllTeams();
  }

  @Override
  public Integer getTeamTotalSteps(String teamId) {
    Optional<ReadLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::readLock);

    return LockUtils.executeWithReadLock(lock, () -> teamService.getTeamTotalSteps(teamId));
  }

  @Override
  public void deleteTeam(String teamId) {
    Optional<WriteLock> lock =
        Optional.ofNullable(lockPerTeamMap.get(teamId)).map(ReentrantReadWriteLock::writeLock);

    // Удаляем lock атомарно внутри критической секции, чтобы избежать race condition
    LockUtils.executeWithWriteLock(lock, () -> {
      teamService.deleteTeam(teamId);
      lockPerTeamMap.remove(teamId);
    });
  }
}
