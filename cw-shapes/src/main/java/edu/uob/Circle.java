package edu.uob;

class Circle extends TwoDimensionalShape {
    int radius;
    static int populationSize = 0;

    public Circle(int r) {
        radius = r;
        populationSize++;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    double calculateArea() {
        return (int) Math.round(Math.PI * radius * radius);
    }

    int calculatePerimeterLength() {
        return (int) Math.round(Math.PI * radius * 2.0);
    }

    public String toString() {
        return "Circle with radius " + radius;
    }
}
