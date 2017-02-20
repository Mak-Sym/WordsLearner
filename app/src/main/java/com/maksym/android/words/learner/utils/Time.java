package com.maksym.android.words.learner.utils;

public class Time {
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) { }
    }
}
