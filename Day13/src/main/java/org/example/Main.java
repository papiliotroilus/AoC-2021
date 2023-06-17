package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Initialise variables
        String inputString;
        int startingRows = 0;
        int startingCols = 0;
        File input = new File("input.txt");
        int dotCount = 0;

        // Read through  file once to determine starting size
        Scanner sizeReader = new Scanner(input);
        while (sizeReader.hasNextLine()) {
            inputString = sizeReader.nextLine();
            // Skip to second half of the file
            if (inputString.equals("")) {
                break;
            }
        }
        while (sizeReader.hasNextLine()) {
            // Break out of loop if sizes known
            if (startingRows != 0 & startingCols != 0) {
                break;
            }
            inputString = sizeReader.nextLine();
            // Process input string to instruction array of axis and number
            String[] foldInstruction = Arrays.stream(inputString.split(" "))
                    .toList()
                    .get(2)
                    .split("=");
            // Fold the sheet vertically
            if (foldInstruction[0].equals("y")) {
                startingCols = (Integer.parseInt(foldInstruction[1])) * 2 + 1;
                // Fold the sheet horizontally
            } else if (foldInstruction[0].equals("x")){
                startingRows = (Integer.parseInt(foldInstruction[1])) * 2 + 1;
            }
        }
        sizeReader.close();

        // Initialise origami sheet
        Map2D origamiSheet = new Map2D(startingCols, startingRows);

        // Read through file again to add points to file and fold it
        Scanner inputReader = new Scanner(input);
        // Read through first half of file to add points to origami sheet
        while (inputReader.hasNextLine()) {
            inputString = inputReader.nextLine();
            // Break when input divider is encountered
            if (inputString.equals("")) {
                break;
            }
            // Update variables
            origamiSheet.chart(inputString);
        }
        // Read through second half of file to fold sheet
        while (inputReader.hasNextLine()) {
            inputString = inputReader.nextLine();
            // Process input string to instruction array of axis and number
            String[] foldInstruction = Arrays.stream(inputString.split(" "))
                    .toList()
                    .get(2)
                    .split("=");
            // Fold the sheet vertically
            if (foldInstruction[0].equals("y")) {
                origamiSheet.foldVer(Integer.parseInt(foldInstruction[1]));
                // Fold the sheet horizontally
            } else {
                origamiSheet.foldHor(Integer.parseInt(foldInstruction[1]));
            }
            // Count dots after first fold only for part 1
            if (dotCount == 0) {
                dotCount = origamiSheet.countDots();
            }
        }
        inputReader.close();

        // Print solution
        System.out.println("The solution to part 1 is " + dotCount);
        System.out.println("The solution to part 2 is: ");
        origamiSheet.printMap();
    }
}