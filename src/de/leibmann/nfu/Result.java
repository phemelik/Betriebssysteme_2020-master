package de.leibmann.nfu;

import java.util.LongSummaryStatistics;

public class Result {

    private static int replacements = 0;
    private static LongSummaryStatistics lifetimes = new LongSummaryStatistics();

    public static void addReplacement() {
        replacements++;
    }
    public static void addLifetime(Long lifetime) {
        lifetimes.accept(lifetime);
    }

    public static void showResults() {
        System.out.println("Average Page Lifetime: " + lifetimes.getAverage() + "ms.");
        System.out.println("Replacements: " + replacements);
    }
}
