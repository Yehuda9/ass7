import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IterateCorpusSecondPart extends IterateCorpus {
    private String hyponym;
    private Map<Hypernym, Integer> hypernymIntegerMap;

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
/*
    @Override
    public void sendLineToMatch(File[] files) {
        String line;
        BufferedReader bufferedReader = null;
        for (File file : files) {
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    iterateRegex(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            break;
        }
        countHypernym();
        //printData();
        writeToFile();
    }*/

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
            if (k != getData().getDb().size() - 1) {
                writer.write("\n");
            }
            k += 1;
        }
        assert writer != null;
        writer.close();
    }

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
                this.hypernymIntegerMap.put(hypernym.getKey(), hypernymIntegerMap.get(hypernym.getKey()) + 1 + c);
            }
        }
    }
}
