import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void sumArrayTest() {
        int[] temp = {1,2,3,4,5};
        assertEquals(15, Main.sumArray(temp));
    }
}