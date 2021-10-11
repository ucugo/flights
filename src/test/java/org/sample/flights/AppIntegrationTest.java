package org.sample.flights;

import io.restassured.RestAssured;
import org.sample.flights.dto.FlightSchedule;
import org.sample.flights.error.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public  void init() {
        RestAssured.port = port;
    }

    @Test
    public void testSuccessfulResponseWithSingleData() {
        FlightSchedule[] flightSchedules = RestAssured.get("/flights/schedule/2021-10-09")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(FlightSchedule[].class);

        Assertions.assertEquals(1, flightSchedules.length);
        Assertions.assertEquals("09:00", flightSchedules[0].getDepartureTime());
        Assertions.assertEquals("Antigua", flightSchedules[0].getDestination());
        Assertions.assertEquals("ANU", flightSchedules[0].getDestinationAirport());
        Assertions.assertEquals("VS033", flightSchedules[0].getFlightNumber());
    }

    @Test
    public void testSuccessfulResponseWithNoData() {
        FlightSchedule[] flightSchedules = RestAssured.get("/flights/schedule/2021-10-07")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(FlightSchedule[].class);

        Assertions.assertEquals(0, flightSchedules.length);
    }

    @Test
    public void testForErrorBadRequest() {
        ErrorResponse errorResponse = RestAssured.get("/flights/schedule/10-09-2021")
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .as(ErrorResponse.class);

        Assertions.assertEquals("Failed validation for field: date with value: 10-09-2021 Date must be in ISO format: YYYY-mm-dd",
                errorResponse.getReason());
    }
}