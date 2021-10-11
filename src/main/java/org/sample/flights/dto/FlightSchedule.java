package org.sample.flights.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FlightSchedule {

    private UUID id;
    private String departureTime;
    private String destination;
    private String destinationAirport;
    private String flightNumber;
}
