package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
    @Test
    void specialMovieWith50PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "PG-13 2021 - Action/Adventure", Duration.ofMinutes(90),
                12.5, 1);
        Movie theBatMan = new Movie("The Batman", "PG-13 2022 - Action/Adventure", Duration.ofMinutes(95), 9, 0);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.now()));

        assertEquals(10, spiderMan.calculateTicketPrice(showing));

        Showing showing_25_percentage_discount = new Showing(spiderMan, 10,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 00)));
        assertEquals(9.375, spiderMan.calculateTicketPrice(showing_25_percentage_discount));

        Showing showing_7th_of_month = new Showing(spiderMan, 10,
                LocalDateTime.of(LocalDate.of(2022, 7, 7), LocalTime.now()));
        assertEquals(10.0, spiderMan.calculateTicketPrice(showing_7th_of_month));

        Showing showing_7th_of_month_without_specialdiscount = new Showing(theBatMan, 10,
                LocalDateTime.of(LocalDate.of(2022, 7, 7), LocalTime.now()));
        assertEquals(8, theBatMan.calculateTicketPrice(showing_7th_of_month_without_specialdiscount));

    }
}
