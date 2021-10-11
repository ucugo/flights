package org.sample.flights.service;

import org.sample.flights.dao.FlightData;
import org.sample.flights.constants.FlightDocumentHeader;
import org.sample.flights.dto.FlightSchedule;
import org.sample.flights.repository.BootstrapData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DailyFlightsService {

    private static final String AVAILABILITY_FLAG = "x";
    private final BootstrapData bootstrapData;

    @Autowired
    public DailyFlightsService(BootstrapData bootstrapData) {
        this.bootstrapData = bootstrapData;
    }

    public List<FlightSchedule> findFlightsWithDate(LocalDate localDate) {

        String displayName = localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.UK).toUpperCase();
        return this.bootstrapData.getFlightData().stream()
                .filter(flightData1 -> flightData1.getSchedule().get(FlightDocumentHeader.valueOf(displayName)) != null)
                .filter(flightData1 -> flightData1.getSchedule().get(FlightDocumentHeader.valueOf(displayName)).equals(AVAILABILITY_FLAG))
                .sorted(Comparator.comparing(FlightData::getDepartureTime))
                .map(flightData -> FlightSchedule.builder()
                        .flightNumber(flightData.getFlightNumber())
                        .departureTime(flightData.getDepartureTime())
                        .destinationAirport(flightData.getDestinationAirport())
                        .destination(flightData.getDestination())
                        .id(UUID.randomUUID())
                        .build())
                .collect(Collectors.toUnmodifiableList());

    }
}
