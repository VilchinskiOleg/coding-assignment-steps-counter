package com.example.codingassignmentstepscounter.data;

import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import com.example.codingassignmentstepscounter.domain.model.Team;
import java.util.Optional;

public interface CountersDao {

  Optional<StepsCounter> getCounter(String teamId, String counterId);
}
