package org.example;

import java.util.*;

public class RiskMap {
    public int[][] mapContent; // 2D Array of map contents
    public int[][] printContent; // Modified copy of map contents for debug printing

    // Method for constructing a 2D map object
    public RiskMap(int rows, int columns) {
        mapContent = new int[rows][columns];
        printContent = new int[rows][columns];
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

    // Method for expanding a map into another map
    public void expand(RiskMap inputMap, int factor) {
        // Repeatedly copy input map to output map
        for (int rowCycle = 0; rowCycle < inputMap.mapContent.length; rowCycle++) {
            for (int colCycle = 0; colCycle < factor; colCycle++) {
                System.arraycopy(inputMap.mapContent[rowCycle], 0, mapContent[rowCycle], inputMap.mapContent[0].length * colCycle, inputMap.mapContent[0].length);
            }
        }
        for (int repetition = 1; repetition < factor; repetition++) {
            for (int rowCycle = 0; rowCycle < inputMap.mapContent.length; rowCycle++) {
                System.arraycopy(mapContent[rowCycle], 0, mapContent[inputMap.mapContent.length * repetition + rowCycle], 0, mapContent[0].length);
            }
        }

        // Increment risk values in output map
        int rowIncrement = 0;
        int rowCounter = 0;
        int colIncrement = 0;
        int colCounter = 0;
        for (int mapRow = 0; mapRow < mapContent.length; mapRow++) {
            for (int mapCol = 0; mapCol < mapContent[0].length; mapCol++) {
                mapContent[mapRow][mapCol] = (mapContent[mapRow][mapCol] + colIncrement + rowIncrement) % 9;
                if (mapContent[mapRow][mapCol] == 0) {
                    mapContent[mapRow][mapCol] = 9;
                }
                colCounter += 1;
                if (colCounter == inputMap.mapContent[0].length) {
                    colCounter = 0;
                    colIncrement += 1;
                }
            }
            colCounter = 0;
            colIncrement = 0;
            rowCounter += 1;
            if (rowCounter == inputMap.mapContent.length) {
                rowCounter = 0;
                rowIncrement += 1;
            }
        }
    }

    // Method for printing map for debugging
    public void print() {
        for (var mapRow : mapContent) {
            String formattedString = Arrays.toString(mapRow)
                    .replace(",", "")
                    .replace("[", "")
                    .replace("]", "")
                    .replace(" ", "")
                    .replace("-1", "X")
                    .trim();
            System.out.println(formattedString);
        }
        System.out.println(" ");
    }

    // Method for pathfinding from origin to destination using Dijkstra's algorithm
    public double pathFind(Point2D origin, Point2D destination) {
        // Progress counter for debugging
        double progressCounter = 0;
        int prevPercentage = 0;
        double totalTiles = mapContent.length * mapContent[0].length;
        // Initialise risk to origin map, path to origin map, and queue set
        Map<Point2D, Double> riskToOrigin = new HashMap<>();
//        Map<Point2D, Point2D> pathToOrigin = new HashMap<>();
        Set<Point2D> queue = new HashSet<>();
        Set<Point2D> visited = new HashSet<>();

        // Create map entries for origin, add to queue
        riskToOrigin.put(origin, 0.0);
//        pathToOrigin.put(origin, origin);
        queue.add(origin);

        // Iterate over queue
        while(!queue.isEmpty()) {
            // Determine point in queue with the lowest known risk to origin
            Point2D closestPoint = queue.iterator().next();
            for (var queuePoint : queue) {
                if (riskToOrigin.get(queuePoint) < riskToOrigin.get(closestPoint)) {
                    closestPoint = queuePoint;
                }
            }
            queue.remove(closestPoint);
            visited.add(closestPoint);
            progressCounter += 1;
            int percentage = (int) (100 / totalTiles * progressCounter);
            if (percentage != prevPercentage) {
                System.out.println("pathfinding " + percentage + "% complete");
                prevPercentage = percentage;
            }

            // If current closest point is destination, return risk to origin
            if (closestPoint == destination) {
                return riskToOrigin.get(destination);
            }
            // Determine neighbours of point
            Set<Point2D> neighbours = new HashSet<>();
            if (closestPoint.lat() != 0) {
                neighbours.add(new Point2D(closestPoint.lat() - 1, closestPoint.lon()));
            }
            if (closestPoint.lon() != 0) {
                neighbours.add(new Point2D(closestPoint.lat(), closestPoint.lon() - 1));
            }
            if (closestPoint.lat() != mapContent.length - 1) {
                neighbours.add(new Point2D(closestPoint.lat() + 1, closestPoint.lon()));
            }
            if (closestPoint.lon() != mapContent[0].length - 1) {
                neighbours.add(new Point2D(closestPoint.lat(), closestPoint.lon() + 1));
            }

            // Iterate over each neighbour
            for (var neighbour : neighbours) {
                if (!visited.contains(neighbour)) {
                    double alternateRoute = riskToOrigin.get(closestPoint) + mapContent[neighbour.lat()][neighbour.lon()];
                    // Update neighbour if distance through current point is shorter
                    if (!riskToOrigin.containsKey(neighbour)) {
                        riskToOrigin.put(neighbour, alternateRoute);
                        queue.add(neighbour);
                    }
                    if (alternateRoute < riskToOrigin.get(neighbour)) {
                        riskToOrigin.put(neighbour, alternateRoute);
//                        pathToOrigin.put(neighbour, closestPoint);
                        queue.add(neighbour);
                    }
                }
            }
        }

        // Return total risk from destination to origin
        return riskToOrigin.get(destination);
    }

}