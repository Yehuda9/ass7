import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RawData {
    private BufferedReader text;
    private List<String> lines = new LinkedList<>();

    public RawData(BufferedReader txt) {
        this.text = txt;
        updateDb(text);
    }
    public RawData(){
        this.text = null;
    }
    public List<String> getLines() {
        return lines;
    }

    public void updateDb(BufferedReader text) {
        this.text = text;
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
