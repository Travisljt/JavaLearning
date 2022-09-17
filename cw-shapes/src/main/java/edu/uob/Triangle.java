package edu.uob;

class Triangle extends TwoDimensionalShape implements MultiVariantShape {
    int x;
    int y;
    int z;
    long i,j;
    private final TriangleVariant sort;
    static int populationSize = 0;
    // TODO implement me!
    public Triangle(int xt, int yt, int zt) {
        x = xt;
        y = yt;
        z = zt;

        long p = getLongSize();
        if (p == x) {
            j = z;
            i = y;
        }
        if (p == y) {
            i = x;
            j = z;
        }
        if (p == z) {
            i = x;
            j = y;
        }
        if (i<=0||j<=0||p<=0){
            sort = TriangleVariant.ILLEGAL;
        }else if(i == j && i == p){
            sort = TriangleVariant.EQUILATERAL;
        }else if(p-i==j){
            sort = TriangleVariant.FLAT;
        }else if(p*p -i*i== j*j){
            sort = TriangleVariant.RIGHT;
        }else if(x==y||y==z||x==z){
            sort = TriangleVariant.ISOSCELES;
        }else if(p-i > j){
            sort = TriangleVariant.IMPOSSIBLE;
        }else{
            sort = TriangleVariant.SCALENE;
        }
        Triangle.populationSize++;
    }
    public TriangleVariant getVariant(){return sort;}

    public int getPopulationSize() {
        return populationSize;
    }

    // TODO implement me!
    public double calculateArea() {
        double p = (x + y + z) / 2.0;
        return Math.sqrt(p * (p - x) * (p - y) * (p - z));
    }

    // TODO implement me!
    public int calculatePerimeterLength() {
        return x + y + z;
    }

    public String toString() {
        return "Triangle of three sides " + x + " , " + y + "and " + z;
    }

    public int getLongSize() {
        if (x > y) {
            return Math.max(x, z);
        } else {
            return Math.max(y, z);
        }
    }


}
