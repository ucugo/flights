package org.sample.flights.repository;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.sample.flights.dao.FlightData;
import org.sample.flights.constants.FlightDocumentHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class BootstrapData {


    private final String departuresScheduleFilePath;
    private final  List<FlightData> flightData = new ArrayList<>();

    public BootstrapData(@Value("${departure.schedule.file-path}") String departuresScheduleFilePath) {
        this.departuresScheduleFilePath = departuresScheduleFilePath;
    }

    public List<FlightData> getFlightData() {
        return Collections.unmodifiableList(flightData);
    }

    @PostConstruct
    private List<FlightData> loadData() throws IOException {
        CsvSchema schema = CsvSchema.builder()
                .build().withHeader();

        MappingIterator<Map<String, String>> it = CsvMapper.builder()
                .build()
                .readerForMapOf(String.class)
                .with(schema)
                .readValues(getClass().getResource(departuresScheduleFilePath));

        it.forEachRemaining(stringStringMap -> flightData.add(
                FlightData.builder().destination(stringStringMap.get(FlightDocumentHeader.DESTINATION.getValue()))
                .destinationAirport(stringStringMap.get(FlightDocumentHeader.DESTINATION_AIRPORT.getValue()))
                .departureTime(stringStringMap.get(FlightDocumentHeader.DEPARTURE_TIME.getValue()))
                .flightNumber(stringStringMap.get(FlightDocumentHeader.FLIGHT_NO.getValue()))
                .schedule(Map.of(FlightDocumentHeader.SUNDAY, stringStringMap.get(FlightDocumentHeader.SUNDAY.getValue())
                        , FlightDocumentHeader.MONDAY, stringStringMap.get(FlightDocumentHeader.MONDAY.getValue())
                        , FlightDocumentHeader.TUESDAY, stringStringMap.get(FlightDocumentHeader.TUESDAY.getValue())
                        , FlightDocumentHeader.WEDNESDAY, stringStringMap.get(FlightDocumentHeader.WEDNESDAY.getValue())
                        , FlightDocumentHeader.THURSDAY, stringStringMap.get(FlightDocumentHeader.THURSDAY.getValue())
                        , FlightDocumentHeader.FRIDAY, stringStringMap.get(FlightDocumentHeader.FRIDAY.getValue())
                        , FlightDocumentHeader.SATURDAY, stringStringMap.get(FlightDocumentHeader.SATURDAY.getValue())
                        ))
                .build())
        );

        return flightData;
    }
}
