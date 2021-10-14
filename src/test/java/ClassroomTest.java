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



}