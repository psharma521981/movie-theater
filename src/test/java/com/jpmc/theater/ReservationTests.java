package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTests {

    @Test
    void totalFee() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", "PG-13 2021 - Action/Adventure", Duration.ofMinutes(90), 12.5, 1),
                1,
                LocalDateTime.now()
        );

        assertTrue(new Reservation(customer, showing, 3).totalFee() == 28.5);

        var showing_reservation_without_discount = new Showing(
                new Movie("The Batman", "PG-13 2022 - Action/Adventure", Duration.ofMinutes(95), 9, 0), 10,
                LocalDateTime.now());

        assertTrue(new Reservation(customer, showing_reservation_without_discount, 3).totalFee() == 27);
    }
}
