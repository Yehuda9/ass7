import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Data.
 */
public class Data {
    //map hypernym to list of hyponyms
    private Map<Hypernym, List<Hyponym>> db = new TreeMap<>(new Comparator<Hypernym>() {
        @Override
        public int compare(Hypernym o1, Hypernym o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    });
    private Map<String, Map<String, Integer>> db1 = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    /**
     * Gets db.
     *
     * @return the db
     */
    public Map<Hypernym, List<Hyponym>> getDb() {
        return db;
    }

    public Map<String, Map<String, Integer>> getDb1() {
        return db1;
    }

    /**
     * Contain hypernym boolean.
     *
     * @param hypernym hypernym
     * @return true if db contains hypernym, false otherwise
     */
    public boolean containHypernym(Hypernym hypernym) {
        return db.containsKey(hypernym);
    }
    public boolean containHypernym(String hypernym) {
        return db1.containsKey(hypernym);
    }

    /**
     * Add hypernym to db.
     *
     * @param hypernym    hypernym
     * @param hyponymList hyponym list
     */
    public void addHypernymToDb(Hypernym hypernym, List<Hyponym> hyponymList) {
        db.put(hypernym, hyponymList);
    }
    public void addHypernymToDb(String hypernym, Map<String, Integer> hyponym) {
        db1.put(hypernym, hyponym);
    }

    /**
     * Add hyponym to db.
     *
     * @param hypernym hypernym
     * @param hyponym  hyponym
     */
    public void addHyponymToDb(Hypernym hypernym, Hyponym hyponym) {
        db.get(hypernym).add(hyponym);
    }
    public void addHyponymToDb(String hypernym, String hyponym) {
        db1.get(hypernym).put(hyponym,1);
    }

    /**
     * Increase hyponym.
     *
     * @param hypernym hypernym
     * @param hyponym  hyponym
     */
    public void increaseHyponym(Hypernym hypernym, Hyponym hyponym) {
        db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)).increase();
    }
    public void increaseHyponym(String hypernym, String hyponym) {
        Map<String, Integer> hypernymListOfHyponyms = getHypernymListOfHyponyms(hypernym);
        hypernymListOfHyponyms.put(hyponym,hypernymListOfHyponyms.get(hyponym)+1);
    }

    /**
     * Gets hypernym list of hyponyms.
     *
     * @param hypernym the hypernym
     * @return list of hyponyms belongs to hypernym
     */
    public List<Hyponym> getHypernymListOfHyponyms(Hypernym hypernym) {
        return db.get(hypernym);
    }
    public Map<String, Integer> getHypernymListOfHyponyms(String hypernym) {
        return db1.get(hypernym);
    }

    /**
     * Is hypernym contain hyponym.
     *
     * @param hypernym hypernym
     * @param hyponym  hyponym
     * @return true if hypernym list of hyponym contains hyponym, false otherwise.
     */
    public boolean isHypernymContainHyponym(Hypernym hypernym, Hyponym hyponym) {
        if (!containHypernym(hypernym)) {
            return false;
        }
        return getHypernymListOfHyponyms(hypernym).contains(hyponym);
    }
    public boolean isHypernymContainHyponym(String hypernym, String hyponym) {
        if (!containHypernym(hypernym)) {
            return false;
        }
        return getHypernymListOfHyponyms(hypernym).containsKey(hyponym);
    }

    /**
     * Is hyponym list empty.
     *
     * @param hypernym hypernym
     * @return true is hypernym list of hyponyms is null or empty, false otherwise.
     */
    public boolean isHyponymListEmpty(Hypernym hypernym) {
        return this.getHypernymListOfHyponyms(hypernym) == null || this.getHypernymListOfHyponyms(hypernym).isEmpty();
    }
    public boolean isHyponymListEmpty(String hypernym) {
        return this.getHypernymListOfHyponyms(hypernym) == null || this.getHypernymListOfHyponyms(hypernym).isEmpty();
    }
    /**
     * Add noun phrase to data.
     * if hyponyms list is empty, create new list, else, add hyponyms to list using addHyponym method.
     *
     * @param nounPhrase noun phrase
     */
    public void addNpToData(NounPhrase nounPhrase) {
        if (isHyponymListEmpty(nounPhrase.getHypernym())) {
            addHypernymToDb(nounPhrase.getHypernym(), new LinkedList<>());
        }
        for (Hyponym hyponym : nounPhrase.getHyponymList()) {
            addHyponym(nounPhrase.getHypernym(), hyponym);
        }
    }
    public void addNpToData1(NounPhrase nounPhrase) {
        if (isHyponymListEmpty(nounPhrase.getStringHypernym())) {
            addHypernymToDb(nounPhrase.getStringHypernym(), new HashMap<>());
        }
        for (Map.Entry<String, Integer> hyponym : nounPhrase.getHyponymMap().entrySet()) {
            addHyponym(nounPhrase.getStringHypernym(), hyponym.getKey());
        }
    }

    /**
     * Add hyponym.
     * if hypernym list of hyponyms already contains hyponym, increase hyponym counter,
     * else add hyponym with counter 1.
     *
     * @param hypernym hypernym
     * @param hyponym  hyponym
     */
    protected void addHyponym(Hypernym hypernym, Hyponym hyponym) {
        if (isHypernymContainHyponym(hypernym, hyponym)) {
            increaseHyponym(hypernym, hyponym);
        } else {
            addHyponymToDb(hypernym, hyponym);
        }
    }
    protected void addHyponym(String hypernym, String hyponym) {
        if (isHypernymContainHyponym(hypernym, hyponym)) {
            increaseHyponym(hypernym, hyponym);
        } else {
            addHyponymToDb(hypernym, hyponym);
        }
    }

    /**
     * Sort hyponym list for every hypernym.
     * using compareTo method.
     */
    public void sortHyponymList() {
        getDb().forEach((key, value) -> value.sort(Hyponym::compareTo));
    }

    /**
     * Reduce under 3 hyponyms.
     * if list of hyponyms size is less than 3, delete it.
     */
    public void reduceUnder3hyponyms() {
        Map<Hypernym, List<Hyponym>> dbCopy = new HashMap<>(getDb());
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : dbCopy.entrySet()) {
            if (hypernym.getValue().size() < 3) {
                getDb().remove(hypernym.getKey());
            }
        }
    }
    public void reduceUnder3hyponyms1() {
        Map<String, Map<String,Integer>> db1Copy = new HashMap<>(getDb1());
        for (Map.Entry<String, Map<String,Integer>> hypernym : db1Copy.entrySet()) {
            if (hypernym.getValue().size() < 3) {
                getDb1().remove(hypernym.getKey());
            }
        }
    }
}
