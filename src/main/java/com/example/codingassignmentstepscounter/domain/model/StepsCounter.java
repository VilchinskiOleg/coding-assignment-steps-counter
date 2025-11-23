package com.example.codingassignmentstepscounter.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepsCounter {

  private final String id;
  private String memberFirstName;
  private String memberLastName;
  private volatile int value = 0;

  public StepsCounter(String id) {
    this.id = id;
  }

  public synchronized void incrementValue(int income) {
    value += income;
  }
}
