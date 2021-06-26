import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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

    /**
     * Instantiates a new Iterate corpus second part.
     *
     * @param d data
     * @param o output path
     * @param h hyponym lemma
     */
    public IterateCorpusSecondPart(Data d, String o, String h) {
        super(d, o);
        hypernymIntegerMap = new TreeMap<>(new Comparator<Hypernym>() {
            @Override
            public int compare(Hypernym o1, Hypernym o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        this.hyponym = h;
    }

    @Override
    protected void uniqueBehaviour() {countHypernym();}

    @Override
    protected void writeToFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("../ass7/hypernym_db.txt", StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int k = 0;
        for (Map.Entry<Hypernym, Integer> hypernym : hypernymIntegerMap.entrySet()) {
            assert writer != null;
            writer.write(hypernym.getKey() + ": (" + hypernym.getValue() + ")");
            if (k != hypernymIntegerMap.size() - 1) {
                writer.write("\n");
            }
            k += 1;
        }
        assert writer != null;
        writer.close();
    }

    /**
     * iterate data map and for each hypernym check if it has hyponym lemma in its list,
     * if true, add to hypernymIntegerMap hypernym and hyponym count.
     */
    private void countHypernym() {
        Hyponym hyponym = new Hyponym(this.hyponym, 0);
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : getData().getDb().entrySet()) {
            if (hypernym.getValue().contains(hyponym)) {
                this.hypernymIntegerMap.put(hypernym.getKey(), 0);
                int c = 0;
                for (Hyponym h : hypernym.getValue()) {
                    c = 0;
                    if (h.equals(hyponym)) {
                        c = h.getCount();
                        break;
                    }
                }
                this.hypernymIntegerMap.put(hypernym.getKey(), hypernymIntegerMap.get(hypernym.getKey()) + c);
            }
        }
    }
}
