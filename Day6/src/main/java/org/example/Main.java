package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Read file
        File input = new File("input.txt");
        Scanner inputReader = new Scanner(input);
        String inputString = inputReader.nextLine();
        inputReader.close();
//        Convert input string to array of fish strings
        String[] fishStrings = inputString.split(",");
//        Convert fish strings array to fish integers array
        int[] fishIntegers = Arrays.stream(fishStrings)
                .mapToInt(Integer::parseInt)
                .toArray();
//        Convert fish integers array to fish list
        List<Integer> fishList = Arrays.stream(fishIntegers).boxed().collect(Collectors.toList());
//        Iterate through fish list to simulate reproduction
        for (int day = 1; day < 81; day++) {
            for (int fishIndex = 0; fishIndex < fishList.size(); fishIndex++) {
                int fishTimer = fishList.get(fishIndex);
                if (fishTimer > 0) {
                    fishList.set(fishIndex, fishTimer - 1);
                } else if (fishTimer == 0) {
                    fishList.set(fishIndex, 6);
                    fishList.add(9);
                }
            }
        }
//        Do it again but properly for part 2
//        Create array of how many fish of each time remaining there are
        long[] ageList = new long[9];
        for (var fish : fishIntegers) {
            ageList[fish] += 1;
        }
//        Decrement each timer by day
        for (int day = 1; day < 257; day++) {
            long newSpawn = ageList[0];
            for (int age = 0; age < 8; age++) {
                ageList[age] = ageList[age + 1];
            }
            ageList[6] += newSpawn;
            ageList[8] = newSpawn;
        }

//        Calculate sum of all fish
        long fishSum = 0;
        for (int age = 0; age < 9; age++) {
            fishSum += ageList[age];
        }
//        Print solution
        System.out.println("The solution to part 1 is " + fishList.size());
        System.out.println("The solution to part 2 is " + fishSum);
    }
}