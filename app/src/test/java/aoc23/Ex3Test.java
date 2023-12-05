package aoc23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Ex3Test {
    @Test
    void testA() {
        var classUnderTest = new Ex3();
        assertEquals(classUnderTest.solveA(), 4361);
    }

    @Test
    void testB() {
        var classUnderTest = new Ex3();
        assertEquals(classUnderTest.solveB(), 467835);
    }
}
