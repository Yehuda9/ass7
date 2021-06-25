import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private Map<Hypernym, List<Hyponym>> db = new HashMap<>();

    public boolean containHypernym(Hypernym hypernym) {
        return db.containsKey(hypernym);
    }

    public void addHypernym(Hypernym hypernym, List<Hyponym> hyponymList) {
        db.put(hypernym, hyponymList);
    }

    public void removeHypernym(Hypernym hypernym) {
        db.remove(hypernym);
    }

    public void increaseHyponym(Hypernym hypernym, Hyponym hyponym) {
        db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)).increase();
    }
}
