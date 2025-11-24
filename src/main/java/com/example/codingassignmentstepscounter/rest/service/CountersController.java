package com.example.codingassignmentstepscounter.rest.service;

import com.example.codingassignmentstepscounter.domain.service.CounterService;
import com.example.codingassignmentstepscounter.domain.service.TeamService;
import com.example.codingassignmentstepscounter.mapper.CounterMapper;
import com.example.codingassignmentstepscounter.mapper.TeamMapper;
import com.example.codingassignmentstepscounter.rest.request.CreateCounterReq;
import com.example.codingassignmentstepscounter.rest.model.StepsCounter;
import com.example.codingassignmentstepscounter.rest.model.Team;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Counter Management", description = "APIs for managing Steps Counters in scope of Team")
public class CountersController {

  private final TeamService teamService;
  private final CounterService counterService;

  private final TeamMapper teamMapper;
  private final CounterMapper counterMapper;

  public CountersController(@Qualifier("synchronizedTeamService") TeamService teamService,
      @Qualifier("synchronizedCounterService") CounterService counterService,
      TeamMapper teamMapper, CounterMapper counterMapper) {
    this.teamService = teamService;
    this.counterService = counterService;
    this.teamMapper = teamMapper;
    this.counterMapper = counterMapper;
  }

  @Operation(summary = "Create Counter", description = "Creates new counter for provided team")
  @PostMapping("/teams/{teamId}/counters")
  public Team addCounter(@PathVariable String teamId, @RequestBody CreateCounterReq req) {
    counterService.addCounter(teamId, req.getMemberFirstName(), req.getMemberLastName());
    return teamMapper.toDto(teamService.getTeamRequired(teamId));
  }

  @Operation(summary = "Update Counter", description = "Increments chosen steps counter with provided value")
  @PostMapping("/teams/{teamId}/counters/{counterId}/increment")
  public StepsCounter incrementCounter(@PathVariable String teamId, @PathVariable String counterId,
      @RequestParam Integer steps) {
    var updatedCounter = counterService.incrementCounter(teamId, counterId, steps);
    return counterMapper.toDto(updatedCounter);
  }

  @Operation(summary = "Get Counter", description = "Returns all counters for provided team")
  @GetMapping("/teams/{teamId}/counters")
  public List<StepsCounter> getCounters(@PathVariable String teamId) {
    return counterMapper.toDtoList(counterService.getCounters(teamId));
  }

  @Operation(summary = "Delete Counter", description = "Removes chosen steps counter")
  @DeleteMapping("/teams/{teamId}/counters/{counterId}")
  public Team deleteCounter(@PathVariable String teamId, @PathVariable String counterId) {
    counterService.deleteCounter(teamId, counterId);
    return teamMapper.toDto(teamService.getTeamRequired(teamId));
  }
}
