package com.company.lyskraft.helper;

import java.util.Random;
import java.util.function.Supplier;

public class OneTimePasswordHelper {
    private final static Integer LENGTH = 4;

    public static Supplier<String> createRandomOneTimePassword() {
        return () -> {
            Random random = new Random();
            StringBuilder oneTimePassword = new StringBuilder();
            for (int i = 0; i < LENGTH; i++) {
                int randomNumber = random.nextInt(1, 10);
                oneTimePassword.append(randomNumber);
            }
            return oneTimePassword.toString().trim();
        };
    }
}