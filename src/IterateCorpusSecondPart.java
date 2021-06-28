import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Iterate corpus second part.
 */
public class IterateCorpusSecondPart extends IterateCorpus {
    private String hyponym;
    private Map<Hypernym, Integer> hypernymIntegerMap;
    private Map<String, Integer> hypernymIntegerMap1;

    /**
     * Instantiates a new Iterate corpus second part.
     *
     * @param d data
     * @param o output path
     * @param h hyponym lemma
     */
    public IterateCorpusSecondPart(Data d, String o, String h) {
        super(d, o);
        hypernymIntegerMap1 = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        hypernymIntegerMap = new TreeMap<>(new Comparator<Hypernym>() {
            @Override
            public int compare(Hypernym o1, Hypernym o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        this.hyponym = h;
    }

    @Override
    protected void uniqueBehaviour() {
        countHypernym1();
    }

    @Override
    protected void output() {
        if (hypernymIntegerMap1.isEmpty()){
            System.out.println("The lemma doesn't appear in the corpus.");
            return;
        }
        int k = 0;
        for (Map.Entry<String, Integer> hypernym : hypernymIntegerMap1.entrySet()) {
            System.out.print(hypernym.getKey() + ": (" + hypernym.getValue() + ")");
            if (k != hypernymIntegerMap.size() - 1) {
                System.out.print("\n");
            }
            k += 1;
        }
        for (Map.Entry<Hypernym, Integer> hypernym : hypernymIntegerMap.entrySet()) {
            System.out.print(hypernym.getKey() + ": (" + hypernym.getValue() + ")");
            if (k != hypernymIntegerMap.size() - 1) {
                System.out.print("\n");
            }
            k += 1;
        }
    }

    /**
     * iterate data map and for each hypernym check if it has hyponym lemma in its list,
     * if true, add to hypernymIntegerMap hypernym and hyponym count.
     */
    private void countHypernym() {
        Hyponym hypo = new Hyponym(this.hyponym, 0);
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : getData().getDb().entrySet()) {
            if (hypernym.getValue().contains(hypo)) {
                if (!hypernymIntegerMap.containsKey(hypernym.getKey())) {
                    this.hypernymIntegerMap.put(hypernym.getKey(), 0);
                }
                int c = 0;
                for (Hyponym h : hypernym.getValue()) {
                    if (h.equals(hypo)) {
                        c = h.getCount();
                        break;
                    }
                }
                this.hypernymIntegerMap.put(hypernym.getKey(), hypernymIntegerMap.get(hypernym.getKey()) + c);
            }
        }
    }
    private void countHypernym1() {
        Hyponym hypo = new Hyponym(this.hyponym, 0);
        for (Map.Entry<String, Map<String,Integer>> hypernym : getData().getDb1().entrySet()) {
            if (hypernym.getValue().containsKey(hyponym)) {
                if (!hypernymIntegerMap1.containsKey(hypernym.getKey())) {
                    this.hypernymIntegerMap1.put(hypernym.getKey(), 0);
                }
                int c = 0;
                for (Map.Entry<String, Integer> h : hypernym.getValue().entrySet()) {
                    if (h.getKey().equals(hyponym)) {
                        c = h.getValue();
                        break;
                    }
                }
                this.hypernymIntegerMap1.put(hypernym.getKey(), hypernymIntegerMap1.get(hypernym.getKey()) + c);
            }
        }
    }
}
