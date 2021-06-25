import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IterateCorpus {
    private First first = First.getInstance();
    private Second second = Second.getInstance();
    private Third third = Third.getInstance();
    private Fourth fourth = Fourth.getInstance();
    private Fifth fifth = Fifth.getInstance();
    private List<GeneralBehaviour> RgxList = new LinkedList<>(Arrays.asList(first, second, third, fourth, fifth));
    private Data data;
    private String outputPath;

    public IterateCorpus(Data d, String o) {
        this.data = d;
        this.outputPath = o;
    }

    protected void iterateRegex(String line) {
        NounPhrase nounPhrase = null;
        for (GeneralBehaviour rgx : RgxList) {
            Pattern pattern = Pattern.compile(rgx.getRgx());
            Matcher hypernymMatcher = pattern.matcher(line);
            if (!hypernymMatcher.find()) {
                continue;
            } else {
                hypernymMatcher = pattern.matcher(line);
                hypernymMatcher.find();
            }
            do {
                nounPhrase = rgx.findMatchesInLine(line, rgx);
                if (nounPhrase == null) {
                    continue;
                }
                data.addNpToData(nounPhrase);
            } while (hypernymMatcher.find());
        }
    }

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
        }
        data.reduceUnder3hyponyms();
        data.sortHyponymList();
        printData();
        writeToFile();
    }

    private void printData() {
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : data.getDb().entrySet()) {
            System.out.print(hypernym.getKey() + ": ");
            for (Hyponym hyponym : hypernym.getValue()) {
                System.out.print(hyponym + ", ");
            }
            System.out.println("");
        }
    }

    private void writeToFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(this.outputPath + "/hypernym_db.txt", StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int k = 0;
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : data.getDb().entrySet()) {
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
            if (k != data.getDb().size()-1) {
                writer.write("\n");
            }
            k += 1;
        }
        assert writer != null;
        writer.close();
    }
}
