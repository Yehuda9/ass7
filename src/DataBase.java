import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBase {
    private final String SUCH_AS_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
                    + "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";
    private final String SUCH_NP_AS_RGX =
            "such\\s+(<np>([^<]*)</np>)\\s+as\\s+(<np>([^<]*)</np>)((\\s*,\\s*)(<np>([^<]*)</np>)"
                    + "((\\s*,\\s*\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?)*";
    Pattern patternSuchAsRgx;
    Pattern patternSuchNpAsRgx;
    private RawData rawData;
    private Map<Hypernym, List<Hyponym>> db;

    public DataBase(RawData rwdt) {
        this.rawData = rwdt;
        this.db = new HashMap<>();
        this.patternSuchAsRgx = Pattern.compile(SUCH_AS_RGX);
        this.patternSuchNpAsRgx = Pattern.compile(SUCH_NP_AS_RGX);
    }

    public Map<Hypernym, List<Hyponym>> getDb() {
        return db;
    }

    /*public Hypernym getKeyByValue(Hypernym value) {
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : getDb().entrySet()) {
            if (hypernym.getKey().equals(value)) {
                return hypernym.getKey();
            }
        }
        return null;
    }*/

    /**
     * given hypernym and matcher and hypernym to db map
     * and find all its hyponyms using while loop and addHyponym method.
     *
     * @param hypernym hypernym
     * @param matcher  hyponym matcher
     */
    private void updateHypernym(Hypernym hypernym, Matcher matcher) {
        while (matcher.find()) {
            Hyponym hyponym = new Hyponym(matcher.group(1), 1);
            addHyponym(hypernym, hyponym);
            //db.get(hypernym).add(hyponym);
        }
    }

    /**
     * add new hypernym to db map and find all its hyponyms using while loop and addHyponym method.
     *
     * @param hypernym hypernym
     * @param matcher  hyponym matcher
     */
    private void addHypernym(Hypernym hypernym, Matcher matcher) {
        db.put(hypernym, new LinkedList<>());
        while (matcher.find()) {
            Hyponym hyponym = new Hyponym(matcher.group(1), 1);
            addHyponym(hypernym, hyponym);
            //db.get(hypernym).add(hyponym);
        }
    }

    /**
     * given hypernym and hyponym, if hyponym exist in hypernym increase its value,
     * else, add new hyponym.
     *
     * @param hypernym hypernym
     * @param hyponym  hyponym
     */
    private void addHyponym(Hypernym hypernym, Hyponym hyponym) {
        if (db.get(hypernym).contains(hyponym)) {
            //int i = db.get(hypernym).indexOf(hyponym);
            db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)).increase();
        } else {
            db.get(hypernym).add(hyponym);
        }
    }

    public void findHyponymSuchAs() {
        //String rgx = "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
        //+ "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";
        //Pattern p = Pattern.compile(SUCH_AS_RGX);
        /*try {
            writer = new PrintWriter("C:\\Users\\USER\\IdeaProjects\\ass7\\hypernym_db.txt", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        for (String line : rawData.getLines()) {
            Regex regex = new Regex(SUCH_AS_RGX);
            Matcher hypernymMatcher = patternSuchAsRgx.matcher(line);
            while (hypernymMatcher.find()) {
                String rgx2 = "<np>([^<]*)</np>";
                Pattern p2 = Pattern.compile(rgx2);
                Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
                Hypernym hypernym = new Hypernym(hypernymMatcher.group(1));
                hyponymMatcher.find();
                if (db.containsKey(hypernym)) {
                    updateHypernym(hypernym, hyponymMatcher);
                } else {
                    addHypernym(hypernym, hyponymMatcher);
                }
                /*db.put(hypernym, null);
                //writer.write(m.group(1) + " ");
                List<Hyponym> hyponymList = new LinkedList<>();
                hyponymMatcher.find();
                while (hyponymMatcher.find()) {
                    Hyponym hyponym = new Hyponym(hyponymMatcher.group(1), 0);
                    hyponymList.add(hyponym);
                    db.put(hypernym, hyponymList);
                    //writer.write(m2.group(1) + " ");
                }*/
            }
        }
    }
}
