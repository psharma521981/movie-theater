package com.jpmc.theater;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@SuppressWarnings("deprecation")
public class Theater {

    LocalDateProvider provider;
    private List<Showing> schedule;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
    static {
        objectMapper.registerModule(new JSR310Module());
        objectMapper.setDateFormat(df);
    }

    public Theater(LocalDateProvider provider) {
        this.provider = provider;

        Movie spiderMan = new Movie("Spider-Man: No Way Home", "PG-13 2021 - Action/Adventure", Duration.ofMinutes(90),
                12.5, 1);
        Movie turningRed = new Movie("Turning Red", "PG 2022 - Family/Comedy", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", "PG-13 2022 - Action/Adventure", Duration.ofMinutes(95), 9, 0);
        schedule = List.of(
            new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
            new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
            new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
            new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
            new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
            new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
            new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
            new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
            new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    public void printSchedule() throws JsonProcessingException {

        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        schedule.forEach(s -> System.out
                .println(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovie().getTitle() + " "
                        + s.getMovie().getRunningTime() + " $" + s.getMovieFee()));
        System.out.println("===================================================");

        // Display Schedule in JSON
        System.out.println(objectMapper.writeValueAsString(schedule));

    }

    public static void main(String[] args) throws JsonProcessingException {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
    }
}
