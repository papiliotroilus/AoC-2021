package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Initialise count
        int DepthCounter = 0;
        int CurrentDepth = 0;
        int PenultimateDepth = 0;
        int AntepenultimateDepth = 0;
        int IncreaseCounter = 0;
        int CurrentWindow = 0;
        int PenultimateWindow = 0;
        int WindowIncreaseCounter = 0;
//        Read file
        File input = new File("input.txt");
        Scanner InputReader = new Scanner(input);
        while (InputReader.hasNextLine()) {
//            Update variables
            DepthCounter += 1;
            PenultimateWindow = CurrentWindow;
            AntepenultimateDepth = PenultimateDepth;
            PenultimateDepth = CurrentDepth;
            CurrentDepth = Integer.parseInt(InputReader.nextLine());
            CurrentWindow = CurrentDepth + PenultimateDepth + AntepenultimateDepth;
//            Test part 1
            if ((DepthCounter > 1) & (CurrentDepth > PenultimateDepth)) IncreaseCounter += 1;
//            Test part 2
            if ((DepthCounter > 3) & (CurrentWindow > PenultimateWindow)) WindowIncreaseCounter += 1;
        }
        InputReader.close();
//        Print solution
        System.out.println("The solution to part 1 is " + IncreaseCounter);
        System.out.println("The solution to part 2 is " + WindowIncreaseCounter);
    }
}