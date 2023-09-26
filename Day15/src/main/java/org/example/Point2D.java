package org.example;

public record Point2D(int lat, int lon) {
    @Override
    public int lat() {
        return lat;
    }

    @Override
    public int lon() {
        return lon;
    }
}