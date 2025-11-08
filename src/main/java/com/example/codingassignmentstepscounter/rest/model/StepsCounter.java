package com.example.codingassignmentstepscounter.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepsCounter {

  private String id;
  private String memberFirstName;
  private String memberLastName;
  private Integer value;
}
