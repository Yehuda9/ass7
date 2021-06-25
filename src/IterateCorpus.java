import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

    public IterateCorpus(Data d) {
        this.data = d;
    }

    protected void iterateRegex(String line) {
        NounPhrase nounPhrase = null;
        for (GeneralBehaviour rgx : RgxList) {
            Pattern pattern = Pattern.compile(rgx.getRgx());
            Matcher hypernymMatcher = pattern.matcher(line);
            if (!hypernymMatcher.find()){
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
    }
}
