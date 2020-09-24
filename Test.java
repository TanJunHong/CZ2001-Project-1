// Java code for Testing of Algorithms

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Test {

    private static String readFile(String fileName) throws IOException {

        // StringBuilder allows for faster String building
        StringBuilder text = new StringBuilder();

        // BufferedReader increases the efficiency of reading from a file as compared to only using FileReader
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        while (true) {

            String line = br.readLine();

            // If reach end of file, break out of loop
            if (line == null) {
                break;

			// If there is '>' character, skip that line as it is not a sequence
            } else if (line.charAt(0) == '>') {
                continue;
            }

            text.append(line);
        }

        br.close();

        return text.toString();
    }

    public static void main(String[] args) throws IOException {

        // BufferedReader with InputStreamReader may be faster, however it is negligible and parsing of input is needed.
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter sequence filename:");
        String sequenceFileName = sc.nextLine();

        System.out.println("Enter query filename:");
        String queryFileName = sc.nextLine();

        String sequence = readFile(sequenceFileName);
        String query = readFile(queryFileName);

        System.out.println("Length: " + sequence.length());

        Naive naive = new Naive(query, sequence);
        naive.search();

        KMP kmp = new KMP(query, sequence);
        kmp.search();

        OptimizedKMP optimizedKMP = new OptimizedKMP(query, sequence);
        optimizedKMP.search();

        System.out.println("Time Taken for Naive: " + naive.getTotalTime().toNanos() + "ns (" +
                naive.getTotalTime().toMinutes() + " minutes / " + naive.getTotalTime().toSeconds() + " seconds)");

        System.out.println("Time Taken for KMP: " + kmp.getTotalTime().toNanos() + "ns (" +
                kmp.getTotalTime().toMinutes() + " minutes / " + kmp.getTotalTime().toSeconds() + " seconds)");

        System.out.println("Time Taken for Optimized KMP: " + optimizedKMP.getTotalTime().toNanos() + "ns (" +
                optimizedKMP.getTotalTime().toMinutes() + " minutes / " + optimizedKMP.getTotalTime().toSeconds() +
                " seconds)");

    }
}