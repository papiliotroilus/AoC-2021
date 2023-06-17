package org.example;

import java.awt.*;
import java.util.*;

public class Map2D {
    // Map contents 2D array
    public int[][] mapContent;

    //  Method for constructing a lava map object
    public Map2D(int rows, int columns) {
        mapContent = new int[rows][columns];
    }

    // Method for converting strings to map lines
    public void chart(int rowNumber, String inputLine) {
        // Split line string to int array
        int[] inputChars = Arrays.stream(inputLine.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        // Add point coordinates to map
        System.arraycopy(inputChars, 0, mapContent[rowNumber], 0, inputChars.length);
    }

    // Method for simulating flashes until solutions to both parts are found
    public int[] flashes(int steps) {
        int totalFlashes = 0;
        int flashesUntilLimit = 0;
        int totalSync = 0;
        int syncReached = 0;
        // Iterate through every step
        for (int i = 0; i != -1; i++) {
            // If all units are flashing in sync, record current step number
            if (totalSync == mapContent.length * mapContent[0].length) {
                syncReached = i;
                break;
            } else {
                totalSync = 0;
            }
            // Iterate through every unit on the map
            for (int mapRow = 0; mapRow < mapContent.length; mapRow++) {
                for (int mapCol = 0; mapCol < mapContent[0].length; mapCol++) {
                    // Initialise chain reaction set
                    Set<Point2D> chainReaction = new HashSet<>();
                    // Charge check current point on map
                    Point2D mapPoint = new Point2D(mapRow, mapCol);
                    if (chargeCheck(mapPoint)) {
                        chainReaction.add(mapPoint);
                    }
                    // While there are points in reaction set, explore them
                    while (chainReaction.size() != 0) {
                        Point2D currentPoint = chainReaction.iterator().next();
                        // Set flashing point to -1 then start exploring neighbours
                        mapContent[currentPoint.lat()][currentPoint.lon()] = -1;
                        // Charge check North-West
                        if (currentPoint.lat() != 0 & currentPoint.lon() != 0) {
                            Point2D northwest = new Point2D(currentPoint.lat() - 1, currentPoint.lon() - 1);
                            if (chargeCheck(northwest)) {
                                chainReaction.add(northwest);
                            }
                        }
                        // Charge check North
                        if (currentPoint.lat() != 0) {
                            Point2D north = new Point2D(currentPoint.lat() - 1, currentPoint.lon());
                            if (chargeCheck(north)) {
                                chainReaction.add(north);
                            }
                        }
                        // Charge check North-East
                        if (currentPoint.lat() != 0 & currentPoint.lon() != mapContent[0].length - 1) {
                            Point2D northeast = new Point2D(currentPoint.lat() - 1, currentPoint.lon() + 1);
                            if (chargeCheck(northeast)) {
                                chainReaction.add(northeast);
                            }
                        }
                        // Charge check West
                        if (currentPoint.lon() != 0) {
                            Point2D west = new Point2D(currentPoint.lat(), currentPoint.lon() - 1);
                            if (chargeCheck(west)) {
                                chainReaction.add(west);
                            }
                        }
                        // Charge check East
                        if (currentPoint.lon() != mapContent[0].length - 1) {
                            Point2D east = new Point2D(currentPoint.lat(), currentPoint.lon() + 1);
                            if (chargeCheck(east)) {
                                chainReaction.add(east);
                            }
                        }
                        // Charge check South-West
                        if (currentPoint.lat() != mapContent.length - 1 & currentPoint.lon() != 0) {
                            Point2D southwest = new Point2D(currentPoint.lat() + 1, currentPoint.lon() - 1);
                            if (chargeCheck(southwest)) {
                                chainReaction.add(southwest);
                            }
                        }
                        // Charge check South
                        if (currentPoint.lat() != mapContent.length - 1) {
                            Point2D south = new Point2D(currentPoint.lat() + 1, currentPoint.lon());
                            if (chargeCheck(south)) {
                                chainReaction.add(south);
                            }
                        }
                        // Charge check South-East
                        if (currentPoint.lat() != mapContent.length - 1 & currentPoint.lon() != mapContent[0].length - 1) {
                            Point2D southeast = new Point2D(currentPoint.lat() + 1, currentPoint.lon() + 1);
                            if (chargeCheck(southeast)) {
                                chainReaction.add(southeast);
                            }
                        }
                        chainReaction.remove(currentPoint);
                    }
                }
            }
            // At the end of each cycle, iterate through map again to reset -1 to 0
            for (int mapRow = 0; mapRow < mapContent.length; mapRow++) {
                for (int mapCol = 0; mapCol < mapContent[0].length; mapCol++) {
                    if (mapContent[mapRow][mapCol] == -1) {
                        mapContent[mapRow][mapCol] = 0;
                        totalFlashes += 1;
                        totalSync += 1;
                    }
                }
            }
            // If given step number is reached, record total flashes so far
            flashesUntilLimit = totalFlashes;
//            // Print map for debugging
//            System.out.println("cycle " + (i + 1) + " done");
//            for (var mapRow : mapContent) {
//                String formattedString = Arrays.toString(mapRow)
//                        .replace(",", "")
//                        .replace("[", "")
//                        .replace("]", "")
//                        .replace(" ", "")
//                        .trim();
//                System.out.println(formattedString);
//            }
        }
    int[] returnArray = {flashesUntilLimit, syncReached};
    return returnArray;
    }
    // Method for checking if map points are ready to flash
    private boolean chargeCheck(Point2D point){
        if (mapContent[point.lat()][point.lon()] != -1) {
            mapContent[point.lat()][point.lon()] += 1;
            if (mapContent[point.lat()][point.lon()] > 9) {
                return true;
            }
        }
        return false;
    }
}