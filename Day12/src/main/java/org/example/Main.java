package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Initialise cave map
        NodeMap caveMap = new NodeMap();
        // Read file
        File input = new File("input.txt");
        Scanner inputReader = new Scanner(input);
        while (inputReader.hasNextLine()) {
            String inputString = inputReader.nextLine();
            // Convert input string to list of strings
            List<String> inputStrings = Arrays.asList(inputString.split("-"));
            // Add string list to cave map
            caveMap.chart(inputStrings);
            // Reverse string list then add to cave map again
            Collections.reverse(inputStrings);
            caveMap.chart(inputStrings);
        }
        inputReader.close();

        // Build path set for part 1
        caveMap.nodeWalk1("start");

        // Clear path set in between parts 1 and 2
        caveMap.currentPath.clear();

        // Build path set for part 2
        caveMap.nodeWalk2("start");

        // Print solution
        System.out.println("The solution to part 1 is " + caveMap.part1pathSet.size());
        System.out.println("The solution to part 2 is " + caveMap.part2pathSet.size());
    }
}