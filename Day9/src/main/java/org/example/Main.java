package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("input.txt");
//        Read file once to determine map size
        Scanner sizeReader = new Scanner(input);
        int mapColumns = sizeReader.nextLine().length();
        int mapRows = 1;
        while (sizeReader.hasNextLine()) {
            sizeReader.nextLine();
            mapRows++;
        }
        sizeReader.close();
//        Initialise lava map
        HeightMap lavaMap = new HeightMap(mapRows, mapColumns);
//        Read file again to update the map
        Scanner inputReader = new Scanner(input);
        int rowCounter = 0;
        while (inputReader.hasNextLine()) {
            lavaMap.chart(rowCounter, inputReader.nextLine());
            rowCounter++;
        }
        inputReader.close();
//        Print solution
        System.out.println("The solution to part 1 is " + lavaMap.lowRisk());
        System.out.println("The solution to part 2 is " + lavaMap.largestBasins());
    }
}