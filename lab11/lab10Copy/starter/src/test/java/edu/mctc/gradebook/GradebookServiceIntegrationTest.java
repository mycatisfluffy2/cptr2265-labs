package edu.mctc.gradebook;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GradebookServiceIntegrationTest {

    private Connection connection;
    private StudentRepository repository;
    private GradebookService service;

    @BeforeEach
    void setUp() throws SQLException {
        // Create a fresh in-memory H2 database for each test.
        String databaseName = "gradebook_test_" + System.nanoTime();
        connection = DriverManager.getConnection(
                "jdbc:h2:mem:" + databaseName + ";DB_CLOSE_DELAY=-1");

        repository = new StudentRepository(connection);

        // This mock is only used when testing roster import.
        RosterLoader loader = mock(RosterLoader.class);
        service = new GradebookService(repository, loader);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void repositoryStoresAndRetrievesStudent() throws SQLException {
        repository.addStudent("1001", "Alice");

        Optional<String> result = repository.getStudentName("1001");

        assertEquals(Optional.of("Alice"), result);
        assertEquals(List.of("1001"), repository.listStudentIds());
    }

    @Test
    void serviceCalculatesFinalGradeUsingDatabaseScores() throws SQLException {
        repository.addStudent("1002", "Bob");
        repository.addScore("1002", "homework", 80.0);
        repository.addScore("1002", "homework", 100.0);
        repository.addScore("1002", "exam", 90.0);

        Map<String, Double> weights = Map.of(
                "homework", 0.5,
                "exam", 0.5);

        GradebookService.FinalGrade result
                = service.finalGrade("1002", weights, null);

        assertEquals("1002", result.studentId());
        assertEquals("Bob", result.name());
        assertEquals(90.0, result.percent(), 0.001);
        assertEquals("A-", result.letter());
    }

    @Test
    void importingRosterPersistsStudentsAndScores() throws Exception {
        RosterLoader loader = mock(RosterLoader.class);

        List<RosterLoader.ScoreRecord> records = List.of(
                new RosterLoader.ScoreRecord(
                        "1003", "Carol", "homework", 95.0),
                new RosterLoader.ScoreRecord(
                        "1004", "David", "exam", 88.0));

        Path rosterPath = Path.of("test-roster.csv");
        when(loader.load(rosterPath)).thenReturn(records);

        // Use the real repository and service with the mocked file loader.
        service = new GradebookService(repository, loader);

        int imported = service.importRoster(rosterPath);

        assertEquals(2, imported);
        assertEquals(
                List.of("1003", "1004"),
                repository.listStudentIds());

        assertEquals(
                Optional.of("Carol"),
                repository.getStudentName("1003"));

        assertEquals(
                Map.of("homework", List.of(95.0)),
                repository.getScores("1003"));

        assertEquals(
                Map.of("exam", List.of(88.0)),
                repository.getScores("1004"));

        assertTrue(repository.getStudentName("9999").isEmpty());
    }
}
