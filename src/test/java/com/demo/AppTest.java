package com.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void allSGradesShouldGiveTenCGPA() {
        assertEquals(10.0,
            App.calculateCGPA(new String[]{"S", "S", "S"}, new int[]{4, 3, 2}),
            0.001);
    }

    @Test
    void allAGradesShouldGiveNineCGPA() {
        assertEquals(9.0,
            App.calculateCGPA(new String[]{"A", "A", "A"}, new int[]{4, 3, 2}),
            0.001);
    }

    @Test
    void mixedGradesShouldCalculateCorrectCGPA() {
        // (10*4 + 9*4 + 8*3 + 0*2) / (4+4+3+2) = 100/13
        assertEquals(100.0 / 13.0,
            App.calculateCGPA(
                new String[]{"S", "A", "B", "F"},
                new int[]{4, 4, 3, 2}),
            0.001);
    }

    @Test
    void lowercaseGradesShouldWork() {
        assertEquals(9.5,
            App.calculateCGPA(new String[]{"s", "a"}, new int[]{4, 4}),
            0.001);
    }

    @Test
    void invalidGradeShouldBeRejected() {
        assertThrows(IllegalArgumentException.class, () -> App.getGradePoint("X"));
    }

    @Test
    void invalidCreditsShouldBeRejected() {
        assertThrows(IllegalArgumentException.class,
            () -> App.calculateCGPA(new String[]{"S"}, new int[]{5}));
    }

    @Test
    void mismatchedArraysShouldBeRejected() {
        assertThrows(IllegalArgumentException.class,
            () -> App.calculateCGPA(new String[]{"S", "A"}, new int[]{4}));
    }
}
