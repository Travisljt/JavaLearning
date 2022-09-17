package edu.uob;

class Rectangle extends TwoDimensionalShape {
    int width;
    int height;
    static int populationSize = 0;
    public Rectangle(int w, int h) {
        width = w;
        height = h;
        populationSize++;
    }

    public int getPopulationSize() {
        return populationSize;
    }
    double calculateArea() {
        return width * height;
    }

    int calculatePerimeterLength() {
        return 2 * (width + height);
    }

    public String toString() {
        return "Rectangle of dimensions " + width + " x " + height;
    }
}
