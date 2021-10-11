package org.sample.flights.service;


import org.sample.flights.dao.FlightData;
import org.sample.flights.dto.FlightSchedule;
import org.sample.flights.repository.BootstrapData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sample.flights.constants.FlightDocumentHeader;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class DailyFlightsServiceTest {

    @Mock
    private BootstrapData bootstrapData;

    private DailyFlightsService dailyFlightsService;

    @BeforeEach
    public void init() {
        this.dailyFlightsService = new DailyFlightsService(bootstrapData);
        Mockito.when(bootstrapData.getFlightData()).thenReturn(mockData());
    }

    @Test
    public void testThatFlightsForGivenDateAreReturned() {
        List<FlightSchedule> flightSchedules = dailyFlightsService.findFlightsWithDate(LocalDate.parse("2021-10-08"));
        Assertions.assertFalse(flightSchedules.isEmpty());

        Assertions.assertEquals(2, flightSchedules.size());
        Assertions.assertEquals("09.00", mockData().get(0).getDepartureTime());
        Assertions.assertEquals("10.00", mockData().get(1).getDepartureTime());
    }

    @Test
    @DisplayName("Should return empty result when no schedule is found for a given day")
    public void testThatNoResultIsReturnedWhenThereIsNoScheduledFlight() {
        List<FlightSchedule> flightSchedules = dailyFlightsService.findFlightsWithDate(LocalDate.parse("2021-10-09"));
        Assertions.assertTrue(flightSchedules.isEmpty());
    }

    private List<FlightData> mockData() {
        return List.of(
                FlightData.builder()
                        .departureTime("09.00")
                        .flightNumber("1011")
                        .destination("london")
                        .destinationAirport("lhr")
                        .schedule(Map.of(FlightDocumentHeader.SUNDAY, ""
                                , FlightDocumentHeader.MONDAY, ""
                                , FlightDocumentHeader.TUESDAY, ""
                                , FlightDocumentHeader.WEDNESDAY, ""
                                , FlightDocumentHeader.THURSDAY, ""
                                , FlightDocumentHeader.FRIDAY, "x"
                                , FlightDocumentHeader.SATURDAY, ""
                        ))
                        .build(),
                FlightData.builder()
                        .departureTime("10.00")
                        .flightNumber("2011")
                        .destination("london")
                        .destinationAirport("lhr")
                        .schedule(Map.of(FlightDocumentHeader.SUNDAY, ""
                                , FlightDocumentHeader.MONDAY, ""
                                , FlightDocumentHeader.TUESDAY, ""
                                , FlightDocumentHeader.WEDNESDAY, ""
                                , FlightDocumentHeader.THURSDAY, ""
                                , FlightDocumentHeader.FRIDAY, "x"
                                , FlightDocumentHeader.SATURDAY, ""
                        ))
                        .build());
    }
}