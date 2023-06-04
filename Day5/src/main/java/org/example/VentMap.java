package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class VentMap {
    //    Map contents 2D array
    public int[][] MapContent;
    public boolean IncludeDiagonals;

    //    Method for constructing a vent map object
    public VentMap(boolean Diagonals) {
        MapContent = new int[1000][1000];
        IncludeDiagonals = Diagonals;
    }

    //    Method for converting strings to map lines
    public void Chart(String LineString) {
//        Split line string to coordinate strings then to coordinate strings
        String[] CoordStrings = LineString.split(",| -> ");
//        Convert number strings to integers and add to array
        int[] CoordIntegers = Arrays.stream(CoordStrings)
                .mapToInt(Integer::parseInt)
                .toArray();
//        Copy array to coordinate list
        List<Integer> Coordinates = new ArrayList<>();
        for (int CoordInteger : CoordIntegers) {
            Coordinates.add(CoordInteger);
        }
//        Initialise for expanding coordinate list to ranges for both axes
        List<Point2D> PointCoords = new ArrayList<>();
        Integer[] End1 = {Coordinates.get(0), Coordinates.get(2)};
        Integer[] End2 = {Coordinates.get(1), Coordinates.get(3)};
        int[] LineRange1;
        int[] LineRange2;
//        If latitude is in reverse order, reverse, expand, then reverse back
        if (End1[0] > End1[1]) {
            List<Integer> LineRange1Boxed = new ArrayList<>(IntStream.range(End1[1], End1[0] + 1).boxed().toList());
            Collections.reverse(LineRange1Boxed);
            LineRange1 = LineRange1Boxed.stream().mapToInt(i->i).toArray();
        }
//        Otherwise just expand
        else {
            LineRange1 = IntStream.range(End1[0], End1[1] + 1).toArray();
        }
//        If longitude is in reverse order, reverse, expand, then reverse back
        if (End2[0] > End2[1]) {
            List<Integer> LineRange2Boxed = new ArrayList<>(IntStream.range(End2[1], End2[0] + 1).boxed().toList());
            Collections.reverse(LineRange2Boxed);
            LineRange2 = LineRange2Boxed.stream().mapToInt(i->i).toArray();
        }
//        Otherwise just expand
        else {
            LineRange2 = IntStream.range(End2[0], End2[1] + 1).toArray();
        }
//        Build points out of pairing range increments and add them to list of points
//        Case for horizontal line
        if (LineRange1.length < LineRange2.length) {
            for (int Increment = 0; Increment < LineRange2.length; Increment++) {
                PointCoords.add(new Point2D(LineRange1[0], LineRange2[Increment]));
            }
//        Case for vertical line
        } else if (LineRange1.length > LineRange2.length) {
            for (int Increment = 0; Increment < LineRange1.length; Increment++) {
                PointCoords.add(new Point2D(LineRange1[Increment], LineRange2[0]));
            }
//        Case for diagonal line
        } else if (IncludeDiagonals) {
            for (int Increment = 0; Increment < LineRange1.length; Increment++) {
                PointCoords.add(new Point2D(LineRange1[Increment], LineRange2[Increment]));
            }
        }
//        Add point coordinates to map
        for (var Point : PointCoords){
            MapContent[Point.Long][Point.Lat] += 1;
        }
    }
//    Method for returning number of intersecting points
    public int Intersections() {
        int IntersectionCounter = 0;
        for (int MapLat = 0; MapLat < 1000; MapLat ++) {
            for (int MapLong = 0; MapLong < 1000; MapLong ++) {
                if (MapContent[MapLat][MapLong] > 1) {
                    IntersectionCounter += 1;
                }
            }
        }
        return IntersectionCounter;
    }
}