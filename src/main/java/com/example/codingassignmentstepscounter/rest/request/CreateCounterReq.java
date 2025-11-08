package com.example.codingassignmentstepscounter.rest.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCounterReq {

  private String memberFirstName;
  private String memberLastName;
}
