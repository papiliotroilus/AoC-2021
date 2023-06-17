package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Initialise
        Map2D dumboMap = new Map2D(10, 10);
        //Read file
        File input = new File("input.txt");
        Scanner inputReader = new Scanner(input);
        int lineCounter = 0;
        while (inputReader.hasNextLine()) {
            // Update variables
            dumboMap.chart(lineCounter, inputReader.nextLine());
            lineCounter += 1;
        }
        inputReader.close();
        int[] solutions = dumboMap.flashes(100);
        // Print solution
        System.out.println("The solution to part 1 is " + solutions[0]);
        System.out.println("The solution to part 2 is " + solutions[1]);
    }
}