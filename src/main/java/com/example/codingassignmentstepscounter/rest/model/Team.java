package com.example.codingassignmentstepscounter.rest.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {

  private String id;
  private String name;
  private List<StepsCounter> counters;
  private Integer totalSteps;
}
