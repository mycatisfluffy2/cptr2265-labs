import java.io.FileWriter;
import java.io.IOException;

interface OrderRepository {
    void save(String line);
}

class TextFileOrderRepository implements OrderRepository {
    private final String filename;

    public TextFileOrderRepository(String filename) {
        this.filename = filename;
    }

    public void save(String line) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(line + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Could not save order: " + e.getMessage());
        }
    }
}
