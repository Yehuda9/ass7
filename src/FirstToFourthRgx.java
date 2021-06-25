import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FirstToFourthRgx extends GeneralBehaviour {
    protected void findMatchesInLine(String line, String rgx) {
        Pattern pattern = Pattern.compile(rgx);
        Matcher hypernymMatcher = pattern.matcher(line);
        while (hypernymMatcher.find()) {
            Pattern p2 = Pattern.compile(this.getNP_RGX());
            Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
            hyponymMatcher.find();
            //hypernym is the first match of NP in the whole match
            Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
            System.out.println("found hypernym: " + hypernym);
            addHypernym(hypernym, hyponymMatcher);
        }
    }
}
