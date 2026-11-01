package edu.mctc.gradebook;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GradebookServiceTest {

    @Test
    void importRosterLoadsRecordsAndSavesThem() throws Exception {
        // Arrange: Create mock dependencies
        RosterLoader loader = mock(RosterLoader.class);
        StudentRepository repository = mock(StudentRepository.class);

        // Prepare sample student records
        List<RosterLoader.ScoreRecord> records = List.of(
                new RosterLoader.ScoreRecord("1001", "Alice", "homework", 95.0),
                new RosterLoader.ScoreRecord("1002", "Bob", "exam", 88.0)
        );

        Path path = Path.of("roster.csv");

        // Tell the mock loader what to return
        when(loader.load(path)).thenReturn(records);

        // Inject the mocks into the service
        GradebookService service = new GradebookService(repository, loader);

        // Act: Import the roster
        int imported = service.importRoster(path);

        // Assert: Check the number of records imported
        assertEquals(2, imported);

        // Verify the loader was called
        verify(loader).load(path);

        // Verify the repository received the student information
        verify(repository).addStudent("1001", "Alice");
        verify(repository).addScore("1001", "homework", 95.0);

        verify(repository).addStudent("1002", "Bob");
        verify(repository).addScore("1002", "exam", 88.0);
    }
}
