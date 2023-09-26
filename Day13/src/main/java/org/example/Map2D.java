package org.example;

import java.util.*;

public class Map2D {
    // Map contents 2D array
    public char[][] mapContent;

    // Method for constructing a map object
    public Map2D(int rows, int columns) {
        mapContent = new char[rows][columns];
        for (var mapRow : mapContent) {
            Arrays.fill(mapRow,'.');
        }
    }

    // Method for converting strings to map lines
    public void chart(String inputLine) {
//        Split line string to int array
        int[] inputCoords = Arrays.stream(inputLine.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
//        Add point coordinates to map
        mapContent[inputCoords[1]][inputCoords[0]] = '#';
    }

    // Method for folding map vertically
    public void foldVer(int foldRow) {
        // Copy map to old map buffer and create a new map that has half its number of rows
        char[][] oldContent = mapContent;
        mapContent = new char[foldRow][oldContent[0].length];
        // Copy the rows in the first half of the old map to the new map
        for (int i = 0; i < foldRow; i++) {
            for (int j = 0; j < oldContent[0].length; j++) {
                mapContent[i][j] = oldContent[i][j];
            }
        }
        // Copy the rows in the second half of the old map to the new map in reverse order
        for (int i = 0; i < foldRow; i++) {
            for (int j = 0; j < oldContent[0].length; j++) {
                if (oldContent[oldContent.length - 1 - i][j] == '#') {
                    mapContent[i][j] = oldContent[oldContent.length - 1 - i][j];
                }
            }
        }
    }

    // Method for folding map horizontally
    public void foldHor(int foldCol) {
        // Copy map to old map buffer and create a new map that has half its number of columns
        char[][] oldContent = mapContent;
        mapContent = new char[oldContent.length][foldCol];
        // Copy the first half of each row of the old map to the new map
        for (int i = 0; i < oldContent.length; i++) {
            for (int j = 0; j < foldCol; j++) {
                mapContent[i][j] = oldContent[i][j];
            }
        }
        // Copy the second half of each row of the old map to the new map in reverse order
        for (int i = 0; i < oldContent.length; i++) {
            for (int j = 0; j < foldCol; j++) {
                if (oldContent[i][oldContent[0].length - 1 - j] == '#') {
                    mapContent[i][j] = oldContent[i][oldContent[0].length - 1 - j];
                }
            }
        }
    }

    // Method for counting visible dots
    public int countDots() {
        int dotCount = 0;
        for (var mapRow : mapContent) {
            for (var mapChar : mapRow) {
                if (mapChar == '#') {
                    dotCount += 1;
                }
            }
        }
        return dotCount;
    }

    // Method for printing map
    public void print() {
        for (var mapRow : mapContent) {
            String formattedString = Arrays.toString(mapRow)
                    .replace(",", "")
                    .replace("[", "")
                    .replace("]", "")
                    .replace(" ", "")
                    .trim();
            System.out.println(formattedString);
        }
        System.out.println(" ");
    }
}