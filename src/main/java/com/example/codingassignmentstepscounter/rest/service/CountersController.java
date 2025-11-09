package com.example.codingassignmentstepscounter.rest.service;

import com.example.codingassignmentstepscounter.domain.service.StepsCounterService;
import com.example.codingassignmentstepscounter.mapper.CounterMapper;
import com.example.codingassignmentstepscounter.mapper.TeamMapper;
import com.example.codingassignmentstepscounter.rest.request.CreateCounterReq;
import com.example.codingassignmentstepscounter.rest.model.StepsCounter;
import com.example.codingassignmentstepscounter.rest.model.Team;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Tag(name = "Counter Management", description = "APIs for managing Steps Counters in scope of Team")
public class CountersController {

  private final StepsCounterService stepsCounterService;
  private final TeamMapper teamMapper;
  private final CounterMapper counterMapper;

  @Operation(summary = "Create Counter", description = "Creates new counter for provided team")
  @PostMapping("/teams/{teamId}/counters")
  public Team addCounter(@PathVariable String teamId, @RequestBody CreateCounterReq req) {
    var updatedTeam = stepsCounterService.addCounter(teamId, req.getMemberFirstName(),
        req.getMemberLastName());
    return teamMapper.toDto(updatedTeam);
  }

  @Operation(summary = "Update Counter", description = "Increments chosen steps counter with provided value")
  @PostMapping("/teams/{teamId}/counters/{counterId}/increment")
  public StepsCounter incrementCounter(@PathVariable String teamId, @PathVariable String counterId,
      @RequestParam Integer steps) {
    var updatedCounter = stepsCounterService.incrementCounter(teamId, counterId, steps);
    return counterMapper.toDto(updatedCounter);
  }

  @Operation(summary = "Get Counter", description = "Returns all counters for provided team")
  @GetMapping("/teams/{teamId}/counters")
  public List<StepsCounter> getCounters(@PathVariable String teamId) {
    var counters = stepsCounterService.getCounters(teamId);
    return counterMapper.toDtoList(counters);
  }

  @Operation(summary = "Delete Counter", description = "Removes chosen steps counter")
  @DeleteMapping("/teams/{teamId}/counters/{counterId}")
  public Team deleteCounter(@PathVariable String teamId, @PathVariable String counterId) {
    var updatedTeam = stepsCounterService.deleteCounter(teamId, counterId);
    return teamMapper.toDto(updatedTeam);
  }
}
