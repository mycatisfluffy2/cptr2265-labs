package edu.mctc.gradebook;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class GradingTest {
// TDD: median test

    @Test
    void median_normalScores_returnsMiddleValue() {
        assertEquals(85.0, Grading.median(List.of(80.0, 90.0, 85.0)));
    }

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

    @Test
    void gradePoints_validLetter_returnsPoints() {
        assertEquals(4.0, Grading.gradePoints("A"));
        assertEquals(0.0, Grading.gradePoints("F"));
    }

    @Test
    void gradePoints_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Grading.gradePoints(null));
    }

    @Test
    void gradePoints_invalidLetter_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Grading.gradePoints("Z"));
    }

    // --- Grading.dropLowest Tests ---
    @Test
    void dropLowest_validScores_dropsLowest() {
        List<Double> scores = List.of(80.0, 90.0, 70.0);
        List<Double> result = Grading.dropLowest(scores, 1);
        assertEquals(List.of(80.0, 90.0), result);
    }

    @Test
    void dropLowest_negativeN_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Grading.dropLowest(List.of(80.0), -1));
    }

    @Test
    void dropLowest_dropAll_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> Grading.dropLowest(List.of(80.0), 1));
    }

    //Extra Grading
    @Test
    void weightedAverage_valid_returnsAverage() {
        Map<String, List<Double>> scores = Map.of("labs", List.of(90.0, 80.0));
        Map<String, Double> weights = Map.of("labs", 1.0);
        assertEquals(85.0, Grading.weightedAverage(scores, weights));
    }

    @Test
    void weightedAverage_invalidWeightsSum_throwsException() {
        Map<String, List<Double>> scores = Map.of("labs", List.of(90.0));
        Map<String, Double> weights = Map.of("labs", 0.5);
        assertThrows(IllegalArgumentException.class, () -> Grading.weightedAverage(scores, weights));
    }

    // ROSTER
    @Test
    void rosterLoader_validFile_loadsSuccessfully() throws Exception {
        RosterLoader loader = new RosterLoader();
        var records = loader.load(java.nio.file.Path.of("data", "sample_roster.csv"));
        assertFalse(records.isEmpty());
    }

    @Test
    void rosterLoader_emptyFile_throwsException() throws Exception {
        RosterLoader loader = new RosterLoader();
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("empty", ".csv");
        assertThrows(IllegalArgumentException.class, () -> loader.load(tempFile));
    }

    @Test
    void rosterLoader_missingColumn_throwsException() throws Exception {
        RosterLoader loader = new RosterLoader();
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("bad", ".csv");
        java.nio.file.Files.writeString(tempFile, "student_id,name\n1,Alice");
        assertThrows(IllegalArgumentException.class, () -> loader.load(tempFile));
    }

    @Test
    void rosterLoader_invalidScore_throwsException() throws Exception {
        RosterLoader loader = new RosterLoader();
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("badscore", ".csv");
        java.nio.file.Files.writeString(tempFile, "student_id,name,category,score\n1,Alice,exam,abc");
        assertThrows(IllegalArgumentException.class, () -> loader.load(tempFile));
    }
}
    // STUDENT REPOSITORY
