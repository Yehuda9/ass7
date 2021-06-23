import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBase {
    private final String NP_RGX = "<np>([^<]*)</np>";
    private final String FIRST_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
                    + "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";
    private final String SECOND_RGX =
            "such\\s+(<np>([^<]*)</np>)\\s+as\\s+(<np>([^<]*)</np>)((\\s*,\\s*)(<np>([^<]*)</np>)"
                    + "((\\s*,\\s*\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?)*";
    private final String THIRD_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*including\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
                    + "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";
    private final String FORTH_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*especially\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
                    + "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";
    private List<String> rgxList =
            new LinkedList<>(Arrays.asList(this.FIRST_RGX, this.SECOND_RGX, this.THIRD_RGX, this.FORTH_RGX));
    private RawData rawData;
    private Map<Hypernym, List<Hyponym>> db;

    public DataBase(RawData rwdt) {
        this.rawData = rwdt;
        this.db = new HashMap<>();
    }

    public Map<Hypernym, List<Hyponym>> getDb() {
        return db;
    }

    /**
     * add new hypernym to db map and find all its hyponyms using while loop and addHyponym method.
     *
     * @param hypernym hypernym
     * @param matcher  hyponym matcher
     */
    private void addHypernym(Hypernym hypernym, Matcher matcher) {
        if (!db.containsKey(hypernym)) {
            db.put(hypernym, new LinkedList<>());
        }
        while (matcher.find()) {
            Hyponym hyponym = new Hyponym(matcher.group(1), 1);
            addHyponym(hypernym, hyponym);
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
            db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)).increase();
            //System.out.println("found hyponym: "+db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)));
        } else {
            db.get(hypernym).add(hyponym);
            //System.out.println("found hyponym: "+hyponym);
        }
    }

    private void findMatches(String line, String rgx) {
        Pattern pattern = Pattern.compile(rgx);
        Matcher hypernymMatcher = pattern.matcher(line);
        while (hypernymMatcher.find()) {
            Pattern p2 = Pattern.compile(this.NP_RGX);
            Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
            hyponymMatcher.find();
            //hypernym is the first match of NP in the whole match
            Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
            //System.out.println("found hypernym: " + hypernym);
            addHypernym(hypernym, hyponymMatcher);
        }
    }

    public void findMatches() {
        for (String line : rawData.getLines()) {
            for (String rgx : rgxList) {
                findMatches(line, rgx);
            }
        }
        reduceUnder3hyponyms();
    }

    private void reduceUnder3hyponyms() {
        Map<Hypernym, List<Hyponym>> dbCopy = new HashMap<>(getDb());
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : dbCopy.entrySet()) {
            List<Hyponym> hyponymListCopy = new LinkedList<>(hypernym.getValue());
            for (Hyponym hyponym : hyponymListCopy) {
                if (hyponym.getCount() < 3) {
                    hypernym.getValue().remove(hyponym);
                }
            }
            if (hypernym.getValue().isEmpty()) {
                getDb().remove(hypernym.getKey());
            }
        }
    }
}
