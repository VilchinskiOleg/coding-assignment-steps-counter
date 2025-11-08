package com.example.codingassignmentstepscounter.mapper;

import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CounterMapper {

  com.example.codingassignmentstepscounter.rest.model.StepsCounter toDto(StepsCounter counter);

  List<com.example.codingassignmentstepscounter.rest.model.StepsCounter> toDtoList(
      List<StepsCounter> counters);
}
