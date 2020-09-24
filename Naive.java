// Java code for Naive Brute-Force Algorithm

import java.time.Duration;
import java.time.Instant;

class Naive {

    private final String query;
    private final String sequence;
    private long loopCount;
    private long occurrenceCount;
    private Duration totalTime;

    Naive(String query, String sequence) {
        this.query = query;
        this.sequence = sequence;
        loopCount = 0;
        occurrenceCount = 0;
    }

    Duration getTotalTime() {
        return totalTime;
    }

    void search() {

        // Immutable and thread-safe
        Instant start = Instant.now();

        // If same length, just compare
        if (sequence.length() == query.length()) {
            if (sequence.equals(query)) {
                occurrenceCount++;

                // Print may be omitted as it takes up significant amount of time
                System.out.println("Found at index: 0");
            }

        } else {
            for (int i = 0; i < sequence.length() - query.length(); i++) {
                boolean isMatch = true;
                for (int j = 0; j < query.length(); j++) {
                    loopCount++;
                    if (sequence.charAt(i + j) != query.charAt(j)) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    occurrenceCount++;

                    // Print may be omitted as it takes up significant amount of time
                    System.out.println("Found at index: " + i);
                }

            }
        }

        Instant end = Instant.now();
        System.out.println("Number of found occurrence for Naive: " + occurrenceCount);
        System.out.println("Number of loops for Naive: " + loopCount + "\n");
        totalTime = Duration.between(start, end);
    }
}