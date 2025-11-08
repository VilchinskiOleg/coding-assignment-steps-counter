package com.example.codingassignmentstepscounter.mapper;

import com.example.codingassignmentstepscounter.domain.model.StepsCounter;
import com.example.codingassignmentstepscounter.domain.model.Team;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TeamMapper {

  @Autowired
  private CounterMapper counterMapper;

  @Mapping(target = "counters", source = "counters", qualifiedByName = "mapCounters")
  @Mapping(target = "totalSteps", expression = "java(team.getTotalSteps())")
  public abstract com.example.codingassignmentstepscounter.rest.model.Team toDto(Team team);

  public abstract List<com.example.codingassignmentstepscounter.rest.model.Team> toDtoList(List<Team> teams);

  @Named("mapCounters")
  protected List<com.example.codingassignmentstepscounter.rest.model.StepsCounter> mapCounters(
      ConcurrentHashMap<String, StepsCounter> counters) {
    if (counters == null) {
      return null;
    }
    return counters.values().stream()
        .map(counterMapper::toDto)
        .collect(Collectors.toList());
  }
}
