package edu.mctc.gradebook;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class GradingTest {
// TDD: median test



    // extra
    @Test
    void letterGrade_normalScore_returns_perfectScore() {
        assertEquals("A", Grading.letterGrade(100));
    }

    // 1
    @Test
    void letterGrade_normalScore_returns_A() {
        assertEquals("A", Grading.letterGrade(93));
    }

    // 2
    @Test
    void letterGrade_normalScore_returns_A_minus() {
        assertEquals("A-", Grading.letterGrade(90));
    }

    // 3
    @Test
    void letterGrade_normalScore_returns_B_plus() {
        assertEquals("B+", Grading.letterGrade(87));
    }

    // 4
    @Test
    void letterGrade_normalScore_returns_B() {
        assertEquals("B", Grading.letterGrade(83));
    }

    // 5
    @Test
    void letterGrade_normalScore_returns_B_minus() {
        assertEquals("B-", Grading.letterGrade(80));
    }

    // 6
    @Test
    void letterGrade_normalScore_returns_C_plus() {
        assertEquals("C+", Grading.letterGrade(77));
    }

    // 7
    @Test
    void letterGrade_normalScore_returns_C() {
        assertEquals("C", Grading.letterGrade(73));
    }

    // 8
    @Test
    void letterGrade_normalScore_returns_C_minus() {
        assertEquals("C-", Grading.letterGrade(70));
    }

    // 9
    @Test
    void letterGrade_normalScore_returns_D_plus() {
        assertEquals("D+", Grading.letterGrade(67));
    }

    // 10
    @Test
    void letterGrade_normalScore_returns_D() {
        assertEquals("D", Grading.letterGrade(60));
    }

    // 11
    @Test
    void letterGrade_normalScore_returns_F() {
        assertEquals("F", Grading.letterGrade(59));
    }

    // 12
    @Test
    void letterGrade_above100_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Grading.letterGrade(100.5));
    }

    // 13
    @Test
    void letterGrade_below0_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Grading.letterGrade(-1));
    }

    // 14
    @Test
    void letterGrade_negativeDecimal_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Grading.letterGrade(-0.01));
    }

    // 15
    @Test
    void letterGrade_hugeNumber_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Grading.letterGrade(999.9));
    }
}
