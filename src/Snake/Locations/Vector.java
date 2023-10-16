package Snake.Locations;

public class Vector extends Coordinate {

    /**
     * Create a new Vector (x,y) with an array of length 2.
     *
     * @param coordinate Coordinate of the Vector as an int[]. Must be of length 2.
     */
    public Vector(int[] coordinate) {
        super(coordinate);
    }

    /**
     * Create a new Vector (x,y) with two ints.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Vector(int x, int y) {
        super(new int[]{x, y});
    }

    /**
     * Add this vector to a Coordinate and return the result as a new Coordinate.
     *
     * @param other Coordinate to add to this vector to.
     * @return A new Coordinate representing the result of the addition.
     */
    public Coordinate add(Coordinate other) {
        assert other != null;

        return new Coordinate(new int[]{x() + other.x(), y() + other.y()});
    }

    /**
     * Add this vector to another vector and return the result as a new Vector.
     *
     * @param other Vector to add to this vector to.
     * @return A new Vector representing the result of the addition.
     */
    public Vector add(Vector other) {
        assert other != null;

        return new Vector(new int[]{x() + other.x(), y() + other.y()});
    }

    /**
     * Multiply this vector by a scalar and return the result as a new Vector.
     *
     * @param scalar Scalar to multiply this vector by.
     * @return A new Vector representing the result of the multiplication.
     */
    public Vector multiply(int scalar) {
        return new Vector(new int[]{coordinate[0] * scalar, coordinate[1] * scalar});
    }

    /**
     * Convert this vector to a Coordinate.
     *
     * @return A new Coordinate representing this vector.
     */
    public Coordinate toCoordinate() {
        return new Coordinate(coordinate);
    }

    @Override
    public String toString() {
        return "Vector[" + coordinate[0] + ", " + coordinate[1] + "]";
    }
}
