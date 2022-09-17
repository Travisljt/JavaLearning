package edu.uob;

abstract class TwoDimensionalShape {

  private Colour colour;

  public  Colour getColour(){
    return colour;
  }
  public void setColour(Colour colour){
    this.colour = colour;
  }
  public TwoDimensionalShape() {}

  abstract int getPopulationSize();

  abstract double calculateArea();

  abstract int calculatePerimeterLength();
}
