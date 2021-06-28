import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Fifth rgx.
 */
public abstract class FifthRgx extends GeneralBehaviour {
    /*second match is hypernym and first match is hyponym*/
    @Override
    protected NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        NounPhrase nounPhrase = null;
        Matcher hyponymMatcher = null;
        Pattern pattern = Pattern.compile(rgx.getRgx());
        Matcher hypernymMatcher = pattern.matcher(line);
        while (hypernymMatcher.find()) {
            Pattern p2 = Pattern.compile(GeneralBehaviour.NP_RGX);
            hyponymMatcher = p2.matcher(hypernymMatcher.group());
            hyponymMatcher.find();
            String hyponym = hyponymMatcher.group();
            hyponymMatcher.find();
            Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
            hyponymMatcher = p2.matcher(hyponym);
            nounPhrase = new NounPhrase(hypernym);
        }
        if (hyponymMatcher == null) {
            return null;
        }
        return aggregateNp(hyponymMatcher, nounPhrase);
    }
}
