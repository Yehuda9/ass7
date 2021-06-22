import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataBase {
    private RawData rawData;
    private Map<Hypernym, List<Hyponym>> db;

    public DataBase(RawData rwdt) {
        this.rawData = rwdt;
        this.db = new HashMap<>();
    }

    public Map<Hypernym, List<Hyponym>> getDb() {
        return db;
    }
    public Hypernym getKeyByValue(Hypernym value) {
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym: getDb().entrySet()) {
            if (hypernym.getKey().equals(value)) {
                return hypernym.getKey();
            }
        }
        return null;
    }
    public void findHyponymSuchAs() {
        String rgx = "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
                + "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";
        Pattern p = Pattern.compile(rgx);
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("C:\\Users\\USER\\IdeaProjects\\ass7\\hypernym_db.txt", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : rawData.getLines()) {
            Matcher m = p.matcher(line);
            while (m.find()) {
                assert writer != null;
                String rgx2 = "<np>([^<]*)</np>";
                Pattern p2 = Pattern.compile(rgx2);
                Matcher m2 = p2.matcher(m.group());
                Hypernym hypernym = new Hypernym(m.group(1));
                if (db.containsKey(hypernym)) {
                    hypernym = getKeyByValue(hypernym);
                }
                db.put(hypernym, null);
                //writer.write(m.group(1) + " ");
                List<Hyponym> hyponymList = new LinkedList<>();
                m2.find();
                while (m2.find()) {
                    Hyponym hyponym = new Hyponym(m2.group(1));
                    hyponymList.add(hyponym);
                    db.put(hypernym, hyponymList);
                    //writer.write(m2.group(1) + " ");
                }
                writer.println("");
            }
        }
    }
}
