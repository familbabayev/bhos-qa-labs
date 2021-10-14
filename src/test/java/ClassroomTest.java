import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassroomTest {

    @Test
    void addition() {
        assertEquals(7, Classroom.addition(3,4));
    }

    @Test
    void subtraction() {
        assertEquals(-1, Classroom.subtraction(3,4));
    }

    @Test
    void multiplication() {
        assertEquals(12, Classroom.multiplication(3,4));
    }

    @Test
    void division() {
        assertEquals(2.0, Classroom.division(6,3));
    }

    @Test
    void power() {
        assertEquals(64, Classroom.power(4,3));
    }


}