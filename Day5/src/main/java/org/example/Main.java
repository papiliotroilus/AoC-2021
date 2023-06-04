package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Initialise count
        VentMap MapWithoutDiag = new VentMap(false);
        VentMap MapWithDiag = new VentMap(true);
//        Read file
        File input = new File("input.txt");
        Scanner InputReader = new Scanner(input);
        while (InputReader.hasNextLine()) {
//            Build map
            String MapLine = InputReader.nextLine();
            if (!Objects.equals(MapLine, " ")) {
                MapWithoutDiag.Chart(MapLine);
                MapWithDiag.Chart(MapLine);
            }
        }
        InputReader.close();
//        Print map (for testing)
        /*for (var Row : Map.MapContent){
            System.out.println(Arrays.toString(Row));
        }*/
//        Print solution
        System.out.println("The solution to part 1 is " + MapWithoutDiag.Intersections());
        System.out.println("The solution to part 1 is " + MapWithDiag.Intersections());
    }
}