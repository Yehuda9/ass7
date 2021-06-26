import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Iterate corpus.
 */
public abstract class IterateCorpus {
    private First first = First.getInstance();
    private Second second = Second.getInstance();
    private Third third = Third.getInstance();
    private Fourth fourth = Fourth.getInstance();
    private Fifth fifth = Fifth.getInstance();
    private List<GeneralBehaviour> rgxList = new LinkedList<>(Arrays.asList(first, second, third, fourth, fifth));
    private Data data;
    private String outputPath;

    /**
     * Instantiates a new Iterate corpus.
     *
     * @param d data
     * @param o output path
     */
    public IterateCorpus(Data d, String o) {
        this.data = d;
        this.outputPath = o;
    }

    /**
     * Gets data.
     *
     * @return data
     */
    protected Data getData() {
        return data;
    }

    /**
     * Gets rgx list.
     *
     * @return rgx list
     */
    protected List<GeneralBehaviour> getRgxList() {
        return rgxList;
    }

    /**
     * Gets output path.
     *
     * @return output path
     */
    protected String getOutputPath() {
        return outputPath;
    }

    /**
     * Iterate regex.
     * iterate regex list, if found match to connection between hypernym to hyponym,
     * pass line to findMatchesInLine of regex.
     * for each regex, keep iterating line for all matches of this regex in line.
     *
     * @param line line
     */
    protected void iterateRegex(String line) {
        NounPhrase nounPhrase = null;
        for (GeneralBehaviour rgx : rgxList) {
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

    /**
     * specify behaviour for first and second assignment.
     */
    protected abstract void uniqueBehaviour();

    /**
     * Send line to match.
     * for each file in corpus, iterate file line by line.
     * send each line to iterateRegex method.
     *
     * @param files the files
     */
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
        uniqueBehaviour();
        output();
    }

    /**
     * Write to file or print result.
     */
    protected abstract void output();
}
