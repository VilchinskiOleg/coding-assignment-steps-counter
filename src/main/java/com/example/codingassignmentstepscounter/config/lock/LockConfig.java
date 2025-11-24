package com.example.codingassignmentstepscounter.config.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfig {

  @Bean("lockPerTeamMap")
  public ConcurrentHashMap<String, ReentrantReadWriteLock> configureLockPerTeamMap() {
    return new ConcurrentHashMap<>();
  }
}
