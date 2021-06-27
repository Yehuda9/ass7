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

    /**
     * Gets db.
     *
     * @return the db
     */
    public Map<Hypernym, List<Hyponym>> getDb() {
        return db;
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

    /**
     * Add hypernym to db.
     *
     * @param hypernym    hypernym
     * @param hyponymList hyponym list
     */
    public void addHypernymToDb(Hypernym hypernym, List<Hyponym> hyponymList) {
        db.put(hypernym, hyponymList);
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

    /**
     * Increase hyponym.
     *
     * @param hypernym hypernym
     * @param hyponym  hyponym
     */
    public void increaseHyponym(Hypernym hypernym, Hyponym hyponym) {
        db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)).increase();
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

    /**
     * Is hyponym list empty.
     *
     * @param hypernym hypernym
     * @return true is hypernym list of hyponyms is null or empty, false otherwise.
     */
    public boolean isHyponymListEmpty(Hypernym hypernym) {
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
}
