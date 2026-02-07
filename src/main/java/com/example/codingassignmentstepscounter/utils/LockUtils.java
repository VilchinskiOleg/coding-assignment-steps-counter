package com.example.codingassignmentstepscounter.utils;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class LockUtils {

  public static void executeWithWriteLock(Optional<ReentrantReadWriteLock.WriteLock> lock,
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
          throw new RuntimeException("Operation unavailable now. Please try again later.");
        }
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания
        throw new RuntimeException("Operation interrupted", ex);
      } catch (RuntimeException ex) {
        throw ex; // Пробрасываем RuntimeException как есть
      } catch (Exception ex) {
        throw new RuntimeException("Operation failed", ex);
      }
    }
  }

  public static void executeWithReadLock(Optional<ReentrantReadWriteLock.ReadLock> lock,
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
          throw new RuntimeException("Operation unavailable now. Please try again later.");
        }
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания
        throw new RuntimeException("Operation interrupted", ex);
      } catch (RuntimeException ex) {
        throw ex; // Пробрасываем RuntimeException как есть
      } catch (Exception ex) {
        throw new RuntimeException("Operation failed", ex);
      }
    }
  }

  public static <T> T executeWithWriteLock(Optional<ReentrantReadWriteLock.WriteLock> lock,
                                            Supplier<T> operation) {
      try {
        if (lock.isEmpty()) {
          return operation.get();
        } else if (lock.get().tryLock(3, TimeUnit.SECONDS)) {
          try {
            return operation.get();
          } finally {
            lock.get().unlock();
          }
        } else {
          throw new RuntimeException("Operation unavailable now. Please try again later.");
        }
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания
        throw new RuntimeException("Operation interrupted", ex);
      } catch (RuntimeException ex) {
        throw ex; // Пробрасываем RuntimeException как есть
      }
  }

  public static <T> T executeWithReadLock(Optional<ReentrantReadWriteLock.ReadLock> lock,
                                          Supplier<T> operation) {
      try {
        if (lock.isEmpty()) {
          return operation.get();
        } else if (lock.get().tryLock(3, TimeUnit.SECONDS)) {
          try {
            return operation.get();
          } finally {
            lock.get().unlock();
          }
        } else {
          throw new RuntimeException("Operation unavailable now. Please try again later.");
        }
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания
        throw new RuntimeException("Operation interrupted", ex);
      } catch (RuntimeException ex) {
        throw ex; // Пробрасываем RuntimeException как есть
      }
  }

}
