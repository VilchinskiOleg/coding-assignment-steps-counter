package com.example.codingassignmentstepscounter.utils;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.TimeUnit;

public class LockUtils {

  public static void executeWithLock(Optional<ReentrantReadWriteLock.WriteLock> lock,
                                     Runnable operation) {
    if (lock.isEmpty()) {
      operation.run();
    } else {
      try {
        if (lock.get().tryLock(3, TimeUnit.SECONDS)) {
          try {
            operation.run();
          } finally {
            lock.get().unlock();
          }
        } else {
          throw new RuntimeException("Operation unavailable now. Please try again latter ..");
        }
      } catch (InterruptedException ex) {
        throw new RuntimeException("Operation failed");
      }
    }
  }

  public static <T> T executeWithLock(Optional<ReentrantReadWriteLock.ReadLock> lock,
                                      Callable<T> operation) {
      try {
        if (lock.isEmpty()) {
          return operation.call();
        } else if (lock.get().tryLock(3, TimeUnit.SECONDS)) {
          try {
            return operation.call();
          } finally {
            lock.get().unlock();
          }
        } else {
          throw new RuntimeException("Operation unavailable now. Please try again latter ..");
        }
      } catch (Exception ex) {
        throw new RuntimeException("Operation failed");
      }
  }

}
