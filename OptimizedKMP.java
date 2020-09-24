// Java code for Optimized Knuth-Morris-Pratt Algorithm

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;

class OptimizedKMP {

    private final int[] failureFunction;
    private final HashMap<Character, Integer[]> failureTable;
    private final HashSet<Character> queryCharacters;
    private final String query;
    private final String sequence;
    private long loopCount;
    private long occurrenceCount;
    private Duration totalTime;

    OptimizedKMP(String query, String sequence) {
        this.query = query;
        this.sequence = sequence;
        failureFunction = new int[query.length()];
        loopCount = 0;
        occurrenceCount = 0;
        failureTable = new HashMap<>();
        queryCharacters = new HashSet<>();

        // These could be used for DNA/RNA sequences to speed up, since there are only 5 possible characters
		/*queryCharacters.add('A');
		queryCharacters.add('C');
		queryCharacters.add('G');
		queryCharacters.add('T');
		queryCharacters.add('U');
		*/
    }

    Duration getTotalTime() {
        return totalTime;
    }

    void search() {

        // Immutable and thread-safe
        Instant start = Instant.now();

        if (sequence.length() > query.length()) {
            calcFailureFunction();
            calcFailureTable();

            // Index for query
            int i = 0;

            for (int j = 0; j < sequence.length(); j++) {
                loopCount++;

                if (query.charAt(i) == sequence.charAt(j)) {

                    // If number of matching characters equals length of pattern
                    if (i == failureFunction.length - 1) {

                        // Print may be omitted as it takes up significant amount of time
                        System.out.println("Found at index: " + (j - i));

                        occurrenceCount++;

                        // Change index of query to skip redundant checks
                        if (i > 0) {
                            i = failureTable.get(sequence.charAt(j))[i - 1];
                        }

                    } else {
                        i++;
                    }

                } else {

                    // Change index of query to skip redundant checks if it is not the start of query
                    // Also, don't increment j as we need to compare the character in the sequence again
                    if (i > 0) {
                        i = failureTable.containsKey(sequence.charAt(j)) ?
                                failureTable.get(sequence.charAt(j))[i - 1] : 0;
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
        System.out.println("Number of found occurrences for Optimized KMP: " + occurrenceCount);
        System.out.println("Number of loops for Optimized KMP: " + loopCount + "\n");
        totalTime = Duration.between(start, end);
    }

    private void calcFailureFunction() {

        // Store previous proper prefix length
        int matchLength = 0;

        // 1st element is always 0
        failureFunction[0] = 0;

        // This line could be omitted for DNA sequences, if the above add() are uncommented
        queryCharacters.add(query.charAt(0));

        for (int i = 1; i < failureFunction.length; i++) {
            loopCount++;

            // This line could be omitted for DNA sequences, if the above add() are uncommented
            queryCharacters.add(query.charAt(i));

            // If suffix matches, increment length and update failure function
            if (query.charAt(i) == query.charAt(matchLength)) {
                matchLength++;
                failureFunction[i] = matchLength;

                // If suffix doesn't match
            } else {

                // If the previous index's proper prefix length is 0, that index will be at 0
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
    }

    private void calcFailureTable() {

        for (char queryCharacter : queryCharacters) {

            failureTable.put(queryCharacter, new Integer[query.length()]);

            for (int i = 0; i < query.length(); i++) {

                // If character matches, use the value from failure function and add 1
                if (query.charAt(failureFunction[i]) == queryCharacter) {
                    failureTable.get(queryCharacter)[i] = failureFunction[i] + 1;
                } else {

                    // If character does not match and failure function for that index is 0, update failure table to 0.
                    // If failure function is not 0, use previous index to update failure table
                    failureTable.get(queryCharacter)[i] = (failureFunction[i] == 0) ?
                            0 : failureTable.get(queryCharacter)[failureFunction[i] - 1];
                }
            }
        }

        // Prints Failure Table
		/*System.out.println("Failure Table");
		for(char queryCharacter : queryCharacters) {
			System.out.print(queryCharacter);
			for (int i = 0; i < failureTable.get(queryCharacter).length; i++) {
				System.out.print(" " + failureTable.get(queryCharacter)[i]);
			}
			System.out.println();
		}*/
    }
}