import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public abstract class GeneralBehaviour {

    private final String NP_RGX = "<np>([^<]*)</np>";
    private Map<Hypernym, List<Hyponym>> db;
    private Data data;
    public GeneralBehaviour() {
        this.db = new HashMap<>();
        this.data = new Data();
    }

    protected String getNP_RGX() {
        return NP_RGX;
    }

    protected abstract void findMatchesInLine(String line,GeneralBehaviour rgx);
    protected void addHypernym(Hypernym hypernym, Matcher matcher) {
        if (!db.containsKey(hypernym)) {
            db.put(hypernym, new LinkedList<>());
        }
        while (matcher.find()) {
            Hyponym hyponym = new Hyponym(matcher.group(1), 1);
            addHyponym(hypernym, hyponym);
        }
    }

    protected void addHyponym(Hypernym hypernym, Hyponym hyponym) {
        if (db.get(hypernym).contains(hyponym)) {
            db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)).increase();
            System.out.println("found hyponym: " + db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)));
        } else {
            db.get(hypernym).add(hyponym);
            System.out.println("found hyponym: " + hyponym);
        }
    }
}
