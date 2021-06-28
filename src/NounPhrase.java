import java.util.LinkedList;
import java.util.List;

/**
 * The type Noun phrase.
 */
public class NounPhrase {
    private Hypernym hypernym;
    private List<Hyponym> hyponymList;

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

    /**
     * Instantiates a new Noun phrase.
     *
     * @param hyper hypernym
     */
    public NounPhrase(Hypernym hyper) {
        this(hyper, new LinkedList<>());
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
}
