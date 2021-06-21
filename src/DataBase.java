import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBase {
    private RawData rawData;
    private Map<String, List<Hyponym>> db;

    public DataBase(RawData rwdt) {
        this.rawData = rwdt;
        this.db = new TreeMap<>();
    }

    public void findHyponymSuchAs() {
        String rgx = "(<np>[^<]*</np>\\s+)(such\\s+as\\s+)(<np>[^<]*</np>)"
                + "((\\s*(,)?\\s*)(\\s*or\\s*|\\s*and\\s*)?(<np>[^<]*</np>))*";
        Pattern p = Pattern.compile(rgx);
        PrintWriter writer = null;
        try {
            writer =
                    new PrintWriter("C:\\Users\\USER\\IdeaProjects\\ass7\\hypernym_db.txt", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : rawData.getLines()) {
            Matcher m = p.matcher(line);
            while (m.find()) {
                assert writer != null;
                writer.println(m.group());
                System.out.println(m.group());
            }
        }
    }
}
