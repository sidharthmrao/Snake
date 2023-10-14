package Snake.Locations;

import static org.junit.jupiter.api.Assertions.*;

import Snake.Locations.Coordinate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CoordinateTest {
    @Test
    @DisplayName("Creating a new Coordinate should set the coordinate correctly.")
    void testCoordinate() {
        Coordinate coordinate = new Coordinate(new int[] {5, 5});

        assertEquals(5, coordinate.coordinate()[0],
                "Coordinate should be set correctly.");
        assertEquals(5, coordinate.coordinate()[1],
                "Coordinate should be set correctly.");
    }

    @Test
    @DisplayName("Updating a Coordinate should set the coordinate correctly.")
    void testUpdate() {
        Coordinate coordinate = new Coordinate(new int[] {5, 5});
        coordinate.update(new int[] {6, 6});

        assertEquals(6, coordinate.coordinate()[0],
                "Coordinate should be updated correctly.");
        assertEquals(6, coordinate.coordinate()[1],
                "Coordinate should be updated correctly.");
    }

    @Test
    @DisplayName("Comparing two Coordinates should return true if they are equal.")
    void testEquals() {
        Coordinate coordinate1 = new Coordinate(new int[] {5, 5});
        Coordinate coordinate2 = new Coordinate(new int[] {5, 5});

        assertEquals(coordinate1, coordinate2, "Coordinates should be equal.");
    }

    @Test
    @DisplayName("Comparing two Coordinates should return false if they are not equal.")
    void testNotEquals() {
        Coordinate coordinate1 = new Coordinate(new int[] {5, 5});
        Coordinate coordinate2 = new Coordinate(new int[] {6, 6});

        assertNotEquals(coordinate1, coordinate2, "Coordinates should not be equal.");
    }
}