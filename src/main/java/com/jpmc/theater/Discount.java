package com.jpmc.theater;

import java.time.LocalDateTime;

public class Discount {
    private static int MOVIE_CODE_SPECIAL = 1;
    private static int DISCOUNT_CODE_1 = 1;
    private static int DISCOUNT_CODE_2 = 2;
    private static double DISCOUNT_25_PERCENT = 0.25d;
    private static double DISCOUNT_20_PERCENT = 0.2d;


    private double discount;

    /**
     * @return the discount
     * 
     * 
     *         Calculate discount on the basis discount rules
     */
    public void calculateDiscount(Movie movie, Showing showing) {

        double specialDiscount = 0;
        int showSequence = showing.getSequenceOfTheDay();

        if (MOVIE_CODE_SPECIAL == movie.getSpecialCode()) {
            specialDiscount = movie.getTicketPrice() * DISCOUNT_20_PERCENT; // 20% discount for special movie
        }

        double sequenceDiscount = 0;
        if (showSequence == DISCOUNT_CODE_1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showSequence == DISCOUNT_CODE_2) {
            sequenceDiscount = 2; // $2 discount for 2nd show
        }

        LocalDateTime showTime = showing.getStartTime();

        if (specialTimingDiscount(showing.getStartTime())) { // Check special timing
                sequenceDiscount = Math.max(movie.getTicketPrice() * DISCOUNT_25_PERCENT, sequenceDiscount);
        }

        if (showTime.getDayOfMonth() == 7) { // Calculate discount for 7th of every month
            sequenceDiscount = Math.max(1, sequenceDiscount);
        }

        this.discount = Math.max(sequenceDiscount, specialDiscount); // Return maximum discount
    }

    /**
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    // Calculate discount for special timing (11.00 AM till 4.00 PM)
    private boolean specialTimingDiscount(LocalDateTime showTime) {
        if (showTime.getHour() >= 11 && showTime.getHour() <= 16) {
            if (showTime.getHour() != 16) {
                return true;
            } else if (showTime.getMinute() == 0) {
                return true;
            }
        }
        return false;
    }
}
