package org.sample.flights.controller;

import org.sample.flights.dto.FlightSchedule;
import org.sample.flights.service.DailyFlightsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class AppController {

    private final DailyFlightsService dailyFlightsService;

    public AppController(DailyFlightsService dailyFlightsService) {
        this.dailyFlightsService = dailyFlightsService;
    }

    @GetMapping(path = "/schedule/{date}")
    public ResponseEntity<List<FlightSchedule>> findAvailableFlights(@PathVariable("date")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        return ResponseEntity.ok(dailyFlightsService.findFlightsWithDate(localDate));
    }
}