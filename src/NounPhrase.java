import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The type Noun phrase.
 */
public class NounPhrase {
    private Hypernym hypernym;
    private List<Hyponym> hyponymList;
    private String stringHypernym;
    private Map<String, Integer> hyponymMap;

    public Map<String, Integer> getHyponymMap() {
        return hyponymMap;
    }

    public String getStringHypernym() {
        return stringHypernym;
    }

    /**
     * Instantiates a new Noun phrase.
     *
     * @param hyper hypernym
     * @param hypo  list of hyponyms
     */
    public NounPhrase(Hypernym hyper, List<Hyponym> hypo) {
        this.hypernym = hyper;
        this.hyponymList = hypo;
    }
    public NounPhrase(String hyper, Map<String, Integer> hypo) {
        this.stringHypernym = hyper;
        this.hyponymMap = hypo;
    }

    /**
     * Instantiates a new Noun phrase.
     *
     * @param hyper hypernym
     */
    public NounPhrase(Hypernym hyper) {
        this(hyper, new LinkedList<>());
    }
    public NounPhrase(String hyper) {
        this(hyper, new HashMap<>());
    }
    /**
     * Gets hypernym.
     *
     * @return hypernym
     */
    public Hypernym getHypernym() {
        return hypernym;
    }

    /**
     * Gets hyponym list.
     *
     * @return hyponym list
     */
    public List<Hyponym> getHyponymList() {
        return hyponymList;
    }

    /**
     * Add.
     * add hyponym to hyponyms list.
     *
     * @param hyponym hyponym
     */
    public void add(Hyponym hyponym) {
        hyponymList.add(hyponym);
    }
    public void add(String hyponym) {
        hyponymMap.put(hyponym,1);
    }

}
