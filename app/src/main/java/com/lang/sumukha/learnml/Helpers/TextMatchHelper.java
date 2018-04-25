package com.lang.sumukha.learnml.Helpers;

public class TextMatchHelper {
    public TextMatchHelper(){}

    public double matchText(String originalString, String userString) {
        String longer = originalString, shorter = userString;
        if (originalString.length() < userString.length()) { // longer should always have greater length
            longer = userString; shorter = originalString;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    // Example implementation of the Levenshtein Edit Distance
    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    private static int editDistance(String originalString, String userString) {
        originalString = originalString.toLowerCase();
        userString = userString.toLowerCase();
        int[] costs = new int[userString.length() + 1];
        for (int i = 0; i <= originalString.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= userString.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (originalString.charAt(i - 1) != userString.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[userString.length()] = lastValue;
        }
        return costs[userString.length()];
    }
}
