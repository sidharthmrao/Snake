package Snake.Locations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VectorTest {

    @Test
    @DisplayName("Creating a new Vector should set the vector correctly.")
    void testVector() {
        Vector vector = new Vector(new int[]{5, 5});

        assertEquals(5, vector.x(), "Vector should be set correctly.");
        assertEquals(5, vector.y(), "Vector should be set correctly.");
    }

    @Test
    @DisplayName("Adding two Vectors should return the correct Vector.")
    void testAddVector() {
        Vector vector1 = new Vector(new int[]{5, 6});
        Vector vector2 = new Vector(new int[]{6, 7});
        Vector vector3 = vector1.add(vector2);

        assertEquals(11, vector3.x(), "Vector should be added correctly.");
        assertEquals(13, vector3.y(), "Vector should be added correctly.");

        Vector vector4 = vector1.add(new Vector(new int[]{-5, 0}));
        assertEquals(0, vector4.x(), "Vector should be added correctly.");
        assertEquals(6, vector4.y(), "Vector should be added correctly.");
    }

    @Test
    @DisplayName("Adding a Vector to a Coordinate should return the correct Coordinate.")
    void testAddCoordinate() {
        Vector vector1 = new Vector(new int[]{5, 6});
        Coordinate coordinate1 = new Coordinate(new int[]{6, 7});
        Coordinate coordinate2 = vector1.add(coordinate1);

        assertEquals(11, coordinate2.x(), "Vector should be added correctly.");
        assertEquals(13, coordinate2.y(), "Vector should be added correctly.");

        Coordinate coordinate3 = vector1.add(new Coordinate(new int[]{-5, 0}));
        assertEquals(0, coordinate3.x(), "Vector should be added correctly.");
        assertEquals(6, coordinate3.y(), "Vector should be added correctly.");
    }

    @Test
    @DisplayName("Multiplying a Vector by a scalar should return the correct Vector.")
    void testMultiplyVector() {
        Vector vector1 = new Vector(new int[]{5, 6});
        Vector vector2 = vector1.multiply(2);

        assertEquals(10, vector2.x(), "Vector should be multiplied correctly.");
        assertEquals(12, vector2.y(), "Vector should be multiplied correctly.");

        Vector vector3 = vector1.multiply(-1);
        assertEquals(-5, vector3.x(), "Vector should be multiplied correctly.");
        assertEquals(-6, vector3.y(), "Vector should be multiplied correctly.");
    }

    @Test
    @DisplayName("Converting a Vector to a Coordinate should return the correct Coordinate.")
    void testToCoordinate() {
        Vector vector1 = new Vector(new int[]{5, 6});
        Coordinate coordinate1 = vector1.toCoordinate();

        assertEquals(5, coordinate1.x(), "Vector should be converted correctly.");
        assertEquals(6, coordinate1.y(), "Vector should be converted correctly.");
    }

    @Test
    @DisplayName("Comparing two Vectors should return true if they are equal.")
    void testEquals() {
        Vector vector1 = new Vector(new int[]{5, 5});
        Vector vector2 = new Vector(new int[]{5, 5});

        assertEquals(vector1, vector2, "Vectors should be equal.");
    }

    @Test
    @DisplayName("Comparing two Vectors should return false if they are not equal.")
    void testNotEquals() {
        Vector vector1 = new Vector(new int[]{5, 5});
        Vector vector2 = new Vector(new int[]{6, 6});

        assertNotEquals(vector1, vector2, "Vectors should not be equal.");
    }

}