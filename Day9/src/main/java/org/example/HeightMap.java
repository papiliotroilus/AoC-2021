package org.example;

import java.util.*;

public class HeightMap {
    //    Map contents 2D array
    public int[][] mapContent;

    //    Method for constructing a lava map object
    public HeightMap(int rows, int columns) {
        mapContent = new int[rows][columns];
    }
    //    Method for converting strings to map lines
    public void chart(int rowNumber, String inputLine) {
//        Split line string to int array
        int[] inputChars = Arrays.stream(inputLine.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
//        Add point coordinates to map
        System.arraycopy(inputChars, 0, mapContent[rowNumber], 0, inputChars.length);
    }
    //    Method for returning total risk level of all low points on heightmap
    public int lowRisk() {
        int lowRiskTotal = 0;
        for (int mapRow = 0; mapRow < mapContent.length; mapRow++) {
            for (int mapCol = 0; mapCol < mapContent[0].length; mapCol++) {
//                Move on to next point if current point has a lower point to the North
                if (mapRow != 0) {
                    if (mapContent[mapRow - 1][mapCol] <= mapContent[mapRow][mapCol]) {
                        continue;
                    }
                }
//                Move on to the next point if current point has a lower point to the West
                if (mapCol != 0) {
                    if (mapContent[mapRow][mapCol - 1] <= mapContent[mapRow][mapCol]) {
                        continue;
                    }
                }
//                Move on to the next point if current point has a lower point the East
                if (mapCol != mapContent[0].length - 1) {
                    if (mapContent[mapRow][mapCol + 1] <= mapContent[mapRow][mapCol]) {
                        continue;
                    }
                }
//                Move on to the next point if current point has a lower point to the South
                if (mapRow != mapContent.length - 1) {
                    if (mapContent[mapRow + 1][mapCol] <= mapContent[mapRow][mapCol]) {
                        continue;
                    }
                }
//                If all cardinal directions are higher or equal (or out of bounds) then add point risk level to total
                lowRiskTotal += (1 + mapContent[mapRow][mapCol]);
            }
        }
        return lowRiskTotal;
    }
    //    Method for determining size of 3 largest basins
    public int largestBasins() {
//        Initialise lists
        Set<Point2D> alreadyExplored = new HashSet<>();
        List<Integer> basinList = new ArrayList<>();
        for (int mapRow = 0; mapRow < mapContent.length; mapRow++) {
            for (int mapCol = 0; mapCol < mapContent[0].length; mapCol++) {
//                Skip current point if already in lists
                if (mapContent[mapRow][mapCol] == 9 | alreadyExplored.contains(new Point2D(mapRow, mapCol))) {
                    continue;
                }
//                Otherwise, add point to set of points to explore and current basin list
                Set<Point2D> toExplore = new HashSet<>();
                toExplore.add(new Point2D(mapRow, mapCol));
                Set<Point2D> currentBasin = new HashSet<>();
//                While there are points to explore, explore them
                while (toExplore.size() != 0) {
                    Point2D currentPoint = toExplore.iterator().next();
//                    Add point to explore to current basin and list of already explored points
                    currentBasin.add(new Point2D(currentPoint.lat(), currentPoint.lon()));
                    alreadyExplored.add(new Point2D(currentPoint.lat(), currentPoint.lon()));
//                    Add North point if current point can flow to or from it
                    if (currentPoint.lat() != 0) {
                        Point2D northNeighbour = new Point2D(currentPoint.lat() -1, currentPoint.lon());
                        if (mapContent[northNeighbour.lat()][northNeighbour.lon()] != 9 & !alreadyExplored.contains(northNeighbour)) {
                            toExplore.add(northNeighbour);
                        }
                    }
//                    Add West point if current point can flow to or from it
                    if (currentPoint.lon() != 0) {
                        Point2D westNeighbour = new Point2D(currentPoint.lat(), currentPoint.lon() - 1);
                        if (mapContent[westNeighbour.lat()][westNeighbour.lon()] != 9 & !alreadyExplored.contains(westNeighbour)) {
                            toExplore.add(westNeighbour);
                        }
                    }
//                    Add East point if current point can flow to or from it
                    if (currentPoint.lon() != mapContent[0].length - 1) {
                        Point2D eastNeighbour = new Point2D(currentPoint.lat(), currentPoint.lon() + 1);
                        if (mapContent[eastNeighbour.lat()][eastNeighbour.lon()] != 9 & !alreadyExplored.contains(eastNeighbour)) {
                            toExplore.add(eastNeighbour);
                        }
                    }
//                    Add South point if current point can flow to or from it
                    if (currentPoint.lat() != mapContent.length - 1) {
                        Point2D southNeighbour = new Point2D(currentPoint.lat() + 1, currentPoint.lon());
                        if (mapContent[southNeighbour.lat()][southNeighbour.lon()] != 9 & !alreadyExplored.contains(southNeighbour)){
                            toExplore.add(southNeighbour);
                        }
                    }
                    toExplore.remove(currentPoint);
                }
                basinList.add(currentBasin.size());
                Collections.sort(basinList);
                if (basinList.size() > 3) {
                    basinList.remove(0);
                }
            }
        }
        int basinProduct = 1;
        for (var basin : basinList) {
            basinProduct = basinProduct * basin;
        }
        return basinProduct;
    }
}