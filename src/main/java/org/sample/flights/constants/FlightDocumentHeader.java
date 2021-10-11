package org.sample.flights.constants;

public enum FlightDocumentHeader {

    DESTINATION("Destination"),
    DESTINATION_AIRPORT("Destination Airport IATA"),
    DEPARTURE_TIME("Departure Time"),
    FLIGHT_NO("Flight No"),
    SUNDAY("Sunday"),
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday");

    String value;

    FlightDocumentHeader(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
