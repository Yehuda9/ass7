import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RawData {
    private BufferedReader text;
    private List<String> lines = new LinkedList<>();

    public List<String> getLines() {
        return lines;
    }

    public RawData(BufferedReader txt) {
        this.text = txt;
        updateDb();
    }

    private void updateDb() {
        try {
            String line;
            while ((line = this.text.readLine()) != null) {
                this.addLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addLine(String line) {
        this.lines.add(line);
    }
}
