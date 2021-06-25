import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBase {
    private final String NP_RGX = "<np>([^<]*)</np>";

    private final String FIRST_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";
    private final String SECOND_RGX =
            "such\\s+(<np>([^<]*)</np>)\\s+as\\s+(<np>([^<]*)</np>)((\\s*,\\s*)?(<np>([^<]*)</np>))*"
                    + "(((\\s*,\\s*|\\s*and\\s*|\\s*or\\s*)(<np>([^<]*)</np>)))?";
    /*private final String SECOND_RGX = "such\\s+(<np>([^<]*)</np>)\\s+as\\s+(<np>([^<]*)</np>)((\\s*,\\s*)?"
            + "(<np>([^<]*)</np>))*(((\\s*,\\s*|\\s*and\\s*|\\s*or\\s*)(<np>([^<]*)</np>)))?";*/
    private final String THIRD_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*including\\s*<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";
    private final String FORTH_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*especially\\s*<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";
    /*private final String FIRST_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";*/
    /*private final String SECOND_RGX =
            "such\\s+(<np>([^<]*)</np>)\\s+as\\s+(<np>([^<]*)</np>)((\\s*,\\s*)(<np>([^<]*)</np>)"
                    + "((\\s*,\\s*\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?)*";*/
    /*private final String THIRD_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*including\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
                    + "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";*/
    /*private final String FORTH_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*especially\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
                    + "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";*/
    private final String FIFTH_RGX = "<np>([^<]*)</np>(\\s*,\\s*)?\\s*which\\s+is\\s*"
            + "((\\s*an\\s+example\\s*|\\s*a\\s+kind\\s*|\\s*a\\s+class\\s*)?(of\\s+))?<np>([^<]*)</np>";
    private List<String> rgxList = new LinkedList<>(
            Arrays.asList(this.FIRST_RGX, this.SECOND_RGX, this.THIRD_RGX, this.FORTH_RGX, this.FIFTH_RGX));
    private RawData rawData;
    private Map<Hypernym, List<Hyponym>> db;

    public DataBase(RawData rwdt) {
        this.rawData = rwdt;
        this.db = new HashMap<>();
    }

    public DataBase() {
        this(null);
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
        /*do {
            Hyponym hyponym = new Hyponym(matcher.group(1), 1);
            addHyponym(hypernym, hyponym);
        } while (matcher.find());*/
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
            System.out.println("found hyponym: " + db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)));
        } else {
            db.get(hypernym).add(hyponym);
            //addSorted(db.get(hypernym), hyponym);
            //db.get(hypernym).add(hyponym);
            System.out.println("found hyponym: " + hyponym);
        }
    }

    private void addSorted(List<Hyponym> hyponymList, Hyponym hyponym) {
        if (hyponymList.isEmpty()) {
            hyponymList.add(hyponym);
        } else {
            List<Hyponym> hyponymListCopy = new ArrayList<>(hyponymList);
            Hyponym prev = hyponymListCopy.get(0);
            if (hyponym.getCount() >= prev.getCount()) {
                hyponymList.add(0, hyponym);
                return;
            }
            if (hyponym.getCount() <= hyponymList.get(hyponymList.size() - 1).getCount()) {
                hyponymList.add(hyponymList.size(), hyponym);
                return;
            }
            Hyponym next;
            int i = 1;
            while (i < hyponymListCopy.size()) {
                prev = hyponymListCopy.get(i);
                next = hyponymListCopy.get(i + 1);
                if (hyponym.getCount() <= prev.getCount() && hyponym.getCount() >= next.getCount()) {
                    hyponymList.add(i, hyponym);
                    return;
                }
                i += 1;
            }
            /*for (Hyponym h : hyponymListCopy) {
                if (h.getCount() >= hyponym.getCount()) {
                    hyponymList.add(i + 1, hyponym);
                    return;
                }
                i += 1;
            }*/
        }
    }

    private void fifthRgx(String line) {
        Pattern pattern = Pattern.compile(this.FIFTH_RGX);
        Matcher hypernymMatcher = pattern.matcher(line);
        while (hypernymMatcher.find()) {
            Pattern p2 = Pattern.compile(this.NP_RGX);
            Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
            hyponymMatcher.find();
            String hyponym = hyponymMatcher.group();
            hyponymMatcher.find();
            //hypernym is the first match of NP in the whole match
            Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
            hyponymMatcher = p2.matcher(hyponym);
            //hyponymMatcher.find();
            System.out.println("found hypernym: " + hypernym);
            addHypernym(hypernym, hyponymMatcher);
        }
    }

    private void extractMatches(String line, String rgx) {
        if (rgx.equals(this.FIFTH_RGX)) {fifthRgx(line);} else {
            rgxExceptFifth(line, rgx);
        }
    }

    private void rgxExceptFifth(String line, String rgx) {
        Pattern pattern = Pattern.compile(rgx);
        Matcher hypernymMatcher = pattern.matcher(line);
        while (hypernymMatcher.find()) {
            Pattern p2 = Pattern.compile(this.NP_RGX);
            Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
            hyponymMatcher.find();
            //hypernym is the first match of NP in the whole match
            Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
            System.out.println("found hypernym: " + hypernym);
            addHypernym(hypernym, hyponymMatcher);
        }
    }
/*    private void findMatches(String line, String rgx) {
        Pattern pattern = Pattern.compile(rgx);
        Matcher hypernymMatcher = pattern.matcher(line);
        //hypernymMatcher.find();
        if (rgx.equals(FIFTH_RGX)) {
            fifthRgx(line);
            *//*Pattern p2 = Pattern.compile(this.NP_RGX);
            Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
            Hypernym hypernym = new Hypernym(hypernymMatcher.group(6));
            hyponymMatcher.find();
            //hypernym is the first match of NP in the whole match
            //Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
            //System.out.println("found hypernym: " + hypernym);
            Hyponym hyponym = new Hyponym(hyponymMatcher.group(1), 1);
            addHypernym(hypernym, hyponymMatcher);
            addHyponym(hypernym, hyponym);*//*
        } else {
            //hypernymMatcher.find();
        while (hypernymMatcher.find()) {
            *//*if (rgx.equals(FIFTH_RGX)) {
                fifthRgx(line);
                 *//**//*Pattern p2 = Pattern.compile(this.NP_RGX);
            Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
            Hypernym hypernym = new Hypernym(hypernymMatcher.group(6));
            hyponymMatcher.find();
            //hypernym is the first match of NP in the whole match
            //Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
            //System.out.println("found hypernym: " + hypernym);
            Hyponym hyponym = new Hyponym(hyponymMatcher.group(1), 1);
            addHypernym(hypernym, hyponymMatcher);
            addHyponym(hypernym, hyponym);*//**//*
            } else {*//*
                Pattern p2 = Pattern.compile(this.NP_RGX);
                Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
                hyponymMatcher.find();
                //hypernym is the first match of NP in the whole match
                Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
                System.out.println("found hypernym: " + hypernym);
                addHypernym(hypernym, hyponymMatcher);
            }
        }
    }*/

    public void findMatches(RawData rawData) {
        this.rawData = rawData;
        for (String line : rawData.getLines()) {
            //System.out.println(line);
            for (String rgx : rgxList) {
                extractMatches(line, rgx);
            }
        }
        /*reduceUnder3hyponyms();
        sortHyponymList();*/
    }

    public void sortHypernymMap() {
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : getDb().entrySet()) {
            hypernym.getValue().sort(Hyponym::compareTo);
        }
    }

    public void sortHyponymList(List<Map.Entry<Hypernym, List<Hyponym>>> list) {
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : list) {
            hypernym.getValue().sort(Hyponym::compareTo);
        }
        /*getDb().entrySet().stream()
                .sorted((k1, k2) -> k1.getKey().compareTo(k2.getKey()))
                .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));*/
        //Arrays.stream(getDb().entrySet().toArray()).sorted(Hypernym::compareTo);
    }

    public void reduceUnder3hyponyms(List<Map.Entry<Hypernym, List<Hyponym>>> list) {
        List<Map.Entry<Hypernym, List<Hyponym>>> dbCopy = new LinkedList<>(list);
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : dbCopy) {
            if (hypernym.getValue().size() < 3) {
                list.remove(hypernym);
            }
            /*List<Hyponym> hyponymListCopy = new LinkedList<>(hypernym.getValue());
            for (Hyponym hyponym : hyponymListCopy) {
                if (hyponym.getCount() < 3) {
                    hypernym.getValue().remove(hyponym);
                }
            }
            if (hypernym.getValue().isEmpty()) {
                getDb().remove(hypernym.getKey());
            }*/
        }
    }
}
