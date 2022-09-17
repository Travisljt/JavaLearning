package edu.uob;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// IntelliJ should offer a green play-like button on the left of the test class
// and on individual methods annotated with @Test like shown below.
// These buttons are the entry point to your tests, clicking them will run either all the tests (the
// play button next to the class declaration) or just a specific one (the play button next to the
// test method)
class TriangleTests {

  // Read docs on JUnit first: https://junit.org/junit5/docs/current/user-guide/#writing-tests

  private static void assertShapeVariant(MultiVariantShape shape, TriangleVariant expectedType) {
    assertEquals(
        shape.getVariant(),
        expectedType,
        "failed to classify %s as %s".formatted(shape, expectedType));
  }

  // Equilateral: all equal
  @Test
  void testEquilateral() {
    assertShapeVariant(new Triangle(8, 8, 8), TriangleVariant.EQUILATERAL);
  }

  // Isosceles: any two equal
  @Test
  void testIsosceles() {
    assertShapeVariant(new Triangle(5, 5, 3), TriangleVariant.ISOSCELES);
    assertShapeVariant(new Triangle(5, 3, 5), TriangleVariant.ISOSCELES);
    assertShapeVariant(new Triangle(3, 5, 5), TriangleVariant.ISOSCELES);
    assertShapeVariant(new Triangle(5, 5, 7), TriangleVariant.ISOSCELES);
    assertShapeVariant(new Triangle(5, 7, 5), TriangleVariant.ISOSCELES);
    assertShapeVariant(new Triangle(7, 5, 5), TriangleVariant.ISOSCELES);
  }

  // Scalene: all three different (but not special)
  @Test
  void testScalene() {
    assertShapeVariant(new Triangle(12, 14, 15), TriangleVariant.SCALENE);
    assertShapeVariant(new Triangle(14, 12, 15), TriangleVariant.SCALENE);
    assertShapeVariant(new Triangle(12, 15, 14), TriangleVariant.SCALENE);
    assertShapeVariant(new Triangle(14, 15, 12), TriangleVariant.SCALENE);
    assertShapeVariant(new Triangle(15, 12, 14), TriangleVariant.SCALENE);
    assertShapeVariant(new Triangle(15, 14, 12), TriangleVariant.SCALENE);
  }

  // Right-angled: Pythagoras's theorem
  @Test
  void testRight() {
    assertShapeVariant(new Triangle(5, 12, 13), TriangleVariant.RIGHT);
    assertShapeVariant(new Triangle(12, 5, 13), TriangleVariant.RIGHT);
    assertShapeVariant(new Triangle(5, 13, 12), TriangleVariant.RIGHT);
    assertShapeVariant(new Triangle(12, 13, 5), TriangleVariant.RIGHT);
    assertShapeVariant(new Triangle(13, 5, 12), TriangleVariant.RIGHT);
    assertShapeVariant(new Triangle(13, 12, 5), TriangleVariant.RIGHT);
  }

  // Flat: two sides add up to the third
  @Test
  void testFlat() {
    assertShapeVariant(new Triangle(7, 7, 14), TriangleVariant.FLAT);
    assertShapeVariant(new Triangle(7, 14, 7), TriangleVariant.FLAT);
    assertShapeVariant(new Triangle(14, 7, 7), TriangleVariant.FLAT);
    assertShapeVariant(new Triangle(7, 9, 16), TriangleVariant.FLAT);
    assertShapeVariant(new Triangle(7, 16, 9), TriangleVariant.FLAT);
    assertShapeVariant(new Triangle(9, 16, 7), TriangleVariant.FLAT);
    assertShapeVariant(new Triangle(16, 7, 9), TriangleVariant.FLAT);
  }

  // Impossible: two sides add up to less than the third
  @Test
  void testImpossible() {
    assertShapeVariant(new Triangle(2, 3, 13), TriangleVariant.IMPOSSIBLE);
    assertShapeVariant(new Triangle(2, 13, 3), TriangleVariant.IMPOSSIBLE);
    assertShapeVariant(new Triangle(13, 2, 3), TriangleVariant.IMPOSSIBLE);
  }

  // Illegal: a side is zero
  @Test
  void testZero() {
    assertShapeVariant(new Triangle(0, 0, 0), TriangleVariant.ILLEGAL);
    assertShapeVariant(new Triangle(0, 10, 12), TriangleVariant.ILLEGAL);
    assertShapeVariant(new Triangle(10, 0, 12), TriangleVariant.ILLEGAL);
    assertShapeVariant(new Triangle(10, 12, 0), TriangleVariant.ILLEGAL);
  }

  // Illegal: a side is negative
  @Test
  void testNegative() {
    assertShapeVariant(new Triangle(-1, -1, -1), TriangleVariant.ILLEGAL);
    assertShapeVariant(new Triangle(-1, 10, 12), TriangleVariant.ILLEGAL);
    assertShapeVariant(new Triangle(10, -1, 12), TriangleVariant.ILLEGAL);
    assertShapeVariant(new Triangle(10, 12, -1), TriangleVariant.ILLEGAL);
  }

  // Overflow: check that the program doesn't have overflow problems due to
  // using int, float or double. If there are overflow problems, the program will not say Scalene.
  @Test
  void testOverflow() {
    assertShapeVariant(new Triangle(1100000000, 1705032704, 1805032704), TriangleVariant.SCALENE);
    assertShapeVariant(new Triangle(2000000001, 2000000002, 2000000003), TriangleVariant.SCALENE);
    assertShapeVariant(new Triangle(150000002, 666666671, 683333338), TriangleVariant.SCALENE);
  }
}
