// Java code for Knuth-Morris-Pratt Algorithm

import java.time.Duration;
import java.time.Instant;

class KMP {

    private final int[] failureFunction;
    private final String query;
    private final String sequence;
    private long loopCount;
    private long occurrenceCount;
    private Duration totalTime;

    KMP(String query, String sequence) {
        this.query = query;
        this.sequence = sequence;
        failureFunction = new int[query.length()];
        loopCount = 0;
        occurrenceCount = 0;
    }

    Duration getTotalTime() {
        return totalTime;
    }

    void search() {

        // Immutable and thread-safe
        Instant start = Instant.now();

        if (sequence.length() > query.length()) {
            calcFailureFunction();

            // Index for query
            int i = 0;

            for (int j = 0; j < sequence.length(); j++) {
                loopCount++;

                if (query.charAt(i) == sequence.charAt(j)) {
                    i++;

                    // If number of matching characters equals length of pattern
                    if (i == failureFunction.length) {

                        // Print may be omitted as it takes up significant amount of time
                        System.out.println("Found at index: " + (j - i + 1));

                        occurrenceCount++;

                        // Change index of query to skip redundant checks
                        i = failureFunction[i - 1];

                    }
                } else {

                    // Change index of query to skip redundant checks if it is not the start of query
                    // Also, don't increment j as we need to compare the character in the sequence again
                    if (i > 0) {
                        i = failureFunction[i - 1];
                        j--;
                    }

                }
            }

        // If same length, just compare
        } else if (sequence.length() == query.length()) {
            if (sequence.equals(query)) {
                occurrenceCount++;

                // Print may be omitted as it takes up significant amount of time
                System.out.println("Found at index: 0");
            }
        }

        Instant end = Instant.now();
        System.out.println("Number of found occurrences for KMP: " + occurrenceCount);
        System.out.println("Number of loops for KMP: " + loopCount + "\n");
        totalTime = Duration.between(start, end);
    }

    private void calcFailureFunction() {

        // Store previous proper prefix length
        int matchLength = 0;

        // 1st element is always 0
        failureFunction[0] = 0;

        for (int i = 1; i < failureFunction.length; i++) {
            loopCount++;

            // If suffix matches, increment length and update failure function
            if (query.charAt(i) == query.charAt(matchLength)) {
                matchLength++;
                failureFunction[i] = matchLength;

            // If suffix doesn't match
            } else {

                // If the previous index's proper prefix length is 0, that index will be 0 as well
                if (matchLength == 0) {
                    failureFunction[i] = matchLength;

                } else {

                    // If the previous index's proper prefix length has a value, update length to be that value and
                    // don't increment i
                    matchLength = failureFunction[matchLength - 1];
                    i--;
                }
            }
        }

        // Prints failure function
        /*System.out.println("Failure Function");
        for (int i : failureFunction) {
            System.out.print(i + " ");
        }
        System.out.println();*/
    }
}