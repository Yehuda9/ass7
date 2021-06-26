import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IterateCorpusFirstPart extends IterateCorpus {

    public IterateCorpusFirstPart(Data d, String o) {
        super(d, o);
    }

/*    protected void iterateRegex(String line) {
        NounPhrase nounPhrase = null;
        for (GeneralBehaviour rgx : getRgxList()) {
            Pattern pattern = Pattern.compile(rgx.getRgx());
            Matcher hypernymMatcher = pattern.matcher(line);
            if (!hypernymMatcher.find()) {
                continue;
            } else {
                hypernymMatcher = pattern.matcher(line);
                hypernymMatcher.find();
            }
            do {
                nounPhrase = rgx.findMatchesInLine(hypernymMatcher.group(), rgx);
                if (nounPhrase == null) {
                    continue;
                }
                getData().addNpToData(nounPhrase);
            } while (hypernymMatcher.find());
        }
    }*/

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
        firstPart();
        printData();
        writeToFile();
    }
    protected void firstPart(){
        getData().reduceUnder3hyponyms();
        getData().sortHyponymList();
    }
    private void printData() {
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : getData().getDb().entrySet()) {
            System.out.print(hypernym.getKey() + ": ");
            for (Hyponym hyponym : hypernym.getValue()) {
                System.out.print(hyponym + ", ");
            }
            System.out.println("");
        }
    }
    @Override
    protected void writeToFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(this.getOutputPath() + "/hypernym_db.txt", StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int k = 0;
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : getData().getDb().entrySet()) {
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
            if (k != getData().getDb().size()-1) {
                writer.write("\n");
            }
            k += 1;
        }
        assert writer != null;
        writer.close();
    }
}
