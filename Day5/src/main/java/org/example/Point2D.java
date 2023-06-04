package org.example;

public class Point2D {
//    X and Y coordinates of 2D point
    public int Lat, Long;
//    2D point constructor
    public Point2D(int X, int Y) {
        Lat = X; Long = Y; }
//    Override for printing 2D point coordinates
    @Override
    public String toString() {
        return "(" + Lat + "," + Long + ")";
    }
}