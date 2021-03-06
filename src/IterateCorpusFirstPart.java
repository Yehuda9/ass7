import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * The type Iterate corpus first part.
 */
public class IterateCorpusFirstPart extends IterateCorpus {

    /**
     * Instantiates a new Iterate corpus first part.
     *
     * @param d d data
     * @param o output path
     */
    public IterateCorpusFirstPart(Data d, String o) {
        super(d, o);
    }

    @Override
    protected void output() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(this.getOutputPath() + "/hypernym_db.txt", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int k = 0;
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : getData().getDb().entrySet()) {
            if (hypernym.getValue().size() < 3) {
                continue;
            }
            hypernym.getValue().sort(Hyponym::compareTo);
            assert writer != null;
            writer.write(hypernym.getKey() + ": ");
            int i = 0;
            for (Hyponym hyponym : hypernym.getValue()) {
                if (i == 0) {
                    writer.write(hyponym.toString());

                } else {
                    writer.write(", " + hyponym);
                }
                i += 1;
            }
            if (k != getData().getDb().size() - 1) {
                writer.write("\n");
            }
            k += 1;
        }
        assert writer != null;
        writer.close();
    }
}
