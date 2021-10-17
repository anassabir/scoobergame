package com.anas.scoobergame.util;

import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

public class CommonUtil {

    @Value("${custom.app.util.start_number}")
    private static int RANDOM_INITIAL_NUMBER_START;
    @Value("${custom.app.util.end_number}")
    private static int RANDOM_INITIAL_NUMBER_END;

    private static final int[] MOVE_NUMBERS_ARRAY = {-1, 0, 1};

    public static int getRandomNumber() {
        return (int) Math.floor(Math.random() * (RANDOM_INITIAL_NUMBER_END - RANDOM_INITIAL_NUMBER_START + 1)) + RANDOM_INITIAL_NUMBER_START;
    }

    public static int getRandomMoveNumber() {
        return MOVE_NUMBERS_ARRAY[new Random().nextInt(MOVE_NUMBERS_ARRAY.length)];
    }

    public static void main(String[] args) {
        int idx = new Random().nextInt(MOVE_NUMBERS_ARRAY.length);
        String random = "Value is " + (MOVE_NUMBERS_ARRAY[idx]);

        idx = new Random().nextInt(MOVE_NUMBERS_ARRAY.length);
        random = "Value is " + (MOVE_NUMBERS_ARRAY[idx]);


        idx = new Random().nextInt(MOVE_NUMBERS_ARRAY.length);
        random = "Value is " + (MOVE_NUMBERS_ARRAY[idx]);

    }

}
