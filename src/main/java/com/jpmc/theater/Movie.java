package com.jpmc.theater;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Movie {

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, String description, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.description = description;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the specialCode
     */
    public int getSpecialCode() {
        return specialCode;
    }

    public String getTitle() {
        return title;
    }
    public String getRunningTime() {
        long hour = runningTime.toHours();
        long remainingMin = runningTime.toMinutes() - TimeUnit.HOURS.toMinutes(runningTime.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin,
                handlePlural(remainingMin));
    }

    // (s) post fix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        } else {
            return "s";
        }
    }
    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        Discount discount = new Discount();
        discount.calculateDiscount(this, showing);
        return ticketPrice - discount.getDiscount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}