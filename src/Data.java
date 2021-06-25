import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Data {
    private Map<Hypernym, List<Hyponym>> db = new TreeMap<>(new Comparator<Hypernym>() {
        @Override
        public int compare(Hypernym o1, Hypernym o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    });

    public Map<Hypernym, List<Hyponym>> getDb() {
        return db;
    }

    public void setDb(List<Map.Entry<Hypernym, List<Hyponym>>> list) {
        Map<Hypernym, List<Hyponym>> newDb = new HashMap<>();
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym:list) {
            newDb.put(hypernym.getKey(),hypernym.getValue());
        }
        this.db = newDb;
    }

    public boolean containHypernym(Hypernym hypernym) {
        return db.containsKey(hypernym);
    }

    public void addHypernymToDb(Hypernym hypernym, List<Hyponym> hyponymList) {
        db.put(hypernym, hyponymList);
    }

    public void addHyponymToDb(Hypernym hypernym, Hyponym hyponym) {
        db.get(hypernym).add(hyponym);
    }

    public void removeHypernym(Hypernym hypernym) {
        db.remove(hypernym);
    }

    public void increaseHyponym(Hypernym hypernym, Hyponym hyponym) {
        db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)).increase();
    }

    public List<Hyponym> getHypernymListOfHyponyms(Hypernym hypernym) {
        return db.get(hypernym);
    }

    public boolean isHypernymContainHyponym(Hypernym hypernym, Hyponym hyponym) {
        if (!containHypernym(hypernym)) {
            return false;
        }
        return getHypernymListOfHyponyms(hypernym).contains(hyponym);
    }

    public boolean isHyponymListEmpty(Hypernym hypernym) {
        return this.getHypernymListOfHyponyms(hypernym) == null || this.getHypernymListOfHyponyms(hypernym).isEmpty();
    }

    public void addNpToData(NounPhrase nounPhrase) {
        if (isHyponymListEmpty(nounPhrase.getHypernym())) {
            addHypernymToDb(nounPhrase.getHypernym(), new LinkedList<>());
        }
        for (Hyponym hyponym : nounPhrase.getHyponymList()) {
            addHyponym(nounPhrase.getHypernym(), hyponym);
        }
    }

    protected void addHyponym(Hypernym hypernym, Hyponym hyponym) {
        if (isHypernymContainHyponym(hypernym, hyponym)) {
            increaseHyponym(hypernym, hyponym);
            System.out.println("found hyponym: " + db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)));
        } else {
            addHyponymToDb(hypernym, hyponym);
            System.out.println("found hyponym: " + hyponym);
        }
    }

    public void sortHyponymList() {
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : getDb().entrySet()) {
            hypernym.getValue().sort(Hyponym::compareTo);
        }
    }
    public void reduceUnder3hyponyms() {
        Map<Hypernym, List<Hyponym>> dbCopy = new HashMap<>(getDb());
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : dbCopy.entrySet()) {
            if (hypernym.getValue().size() < 3) {
                getDb().remove(hypernym.getKey());
            }
        }
    }
}
