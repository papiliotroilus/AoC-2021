package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Read file
        File input = new File("input.txt");
        Scanner inputReader = new Scanner(input);
        String inputString = inputReader.nextLine();
        inputReader.close();
//        Convert input string to array of strings
        String[] inputStrings = inputString.split(",");
//        Convert strings array to integers array
        int[] positionList = Arrays.stream(inputStrings)
                .mapToInt(Integer::parseInt)
                .toArray();
        inputReader.close();
//        (Efficient) median calculation for part 1-
        Arrays.sort(positionList);
        int median = (int) (((double)positionList[positionList.length/2] + (double)positionList[positionList.length/2 - 1])/2);
        int totalMedianFuel = 0;
        for (var position : positionList) {
            int medianFuel = Math.abs(position - median);
            totalMedianFuel += medianFuel;
        }
//        (Naive) average calculation for part 2
        int maxPosition = Arrays.stream(positionList).max().getAsInt();
        int totalAverageFuel = 999999999;
        int average;
        for (int meetingPoint = 0; meetingPoint < maxPosition; meetingPoint++) {
            int totalFuel = 0;
            for (var position : positionList) {
                for (int step = 0; step < Math.abs(position - meetingPoint) + 1; step ++)
                    totalFuel += step;
            }
            if (totalFuel < totalAverageFuel) {
                average = meetingPoint;
                totalAverageFuel = totalFuel;
            }
        }
//        Print solution
        System.out.println("The solution to part 1 is " + totalMedianFuel);
        System.out.println("The solution to part 2 is " + totalAverageFuel);
    }
}