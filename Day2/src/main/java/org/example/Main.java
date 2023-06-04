package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Initialise count
        int DepthPart1 = 0;
        int HorPos = 0;
        int DepthPart2 = 0;
        int Aim = 0;
//        Read file
        File input = new File("input.txt");
        Scanner InputReader = new Scanner(input);
        while (InputReader.hasNextLine()) {
            String Instruction = InputReader.nextLine();
//            Determine direction and distance
            if (!Objects.equals(Instruction, " ")) {
                String Direction = Instruction.substring(0, 1);
                int Distance = Integer.parseInt(Instruction.substring(Instruction.length() - 1));
//                Change position
                switch (Direction) {
                    case "u" -> {
                        DepthPart1 -= Distance;
                        Aim -= Distance;
                    }
                    case "d" -> {
                        DepthPart1 += Distance;
                        Aim += Distance;
                    }
                    case "f" -> {
                        HorPos += Distance;
                        DepthPart2 += Aim * Distance;
                    }
                }
            }
        }
        InputReader.close();
//        Print solution
        System.out.println("The solution to part 1 is " + DepthPart1 * HorPos);
        System.out.println("The solution to part 2 is " + DepthPart2 * HorPos);
    }
}