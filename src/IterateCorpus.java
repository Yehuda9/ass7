import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class IterateCorpus {
    private First first = First.getInstance();
    private Second second = Second.getInstance();
    private Third third = Third.getInstance();
    private Fourth fourth = Fourth.getInstance();
    private List<GeneralBehaviour> RgxList = new LinkedList<>(Arrays.asList(first, second, third, fourth));

    protected void iterateRegex(String line) {
        for (GeneralBehaviour rgx : RgxList) {
            rgx.findMatchesInLine(line,rgx);
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
