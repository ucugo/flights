package org.sample.flights.dao;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.sample.flights.constants.FlightDocumentHeader;

import java.util.Map;

@Builder
@Getter
@EqualsAndHashCode
public class FlightData {

    private String departureTime;
    private String destination;
    private String destinationAirport;
    private String flightNumber;
    private Map<FlightDocumentHeader, String> schedule;
}
