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

public abstract class IterateCorpus {
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

    protected Data getData() {
        return data;
    }

    protected List<GeneralBehaviour> getRgxList() {
        return RgxList;
    }
    protected String getOutputPath(){
        return outputPath;
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
                nounPhrase = rgx.findMatchesInLine(hypernymMatcher.group(), rgx);
                if (nounPhrase == null) {
                    continue;
                }
                data.addNpToData(nounPhrase);
            } while (hypernymMatcher.find());
        }
    }

    public abstract void sendLineToMatch(File[] files);
/*
    private void printData() {
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym : data.getDb().entrySet()) {
            System.out.print(hypernym.getKey() + ": ");
            for (Hyponym hyponym : hypernym.getValue()) {
                System.out.print(hyponym + ", ");
            }
            System.out.println("");
        }
    }*/

    protected abstract void writeToFile();
}
