import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type First to fourth rgx.
 */
public abstract class FirstToFourthRgx extends GeneralBehaviour {
    /*first match is hypernym, rest of the sentence is hyponyms*/
    @Override
    protected NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        Pattern pattern = Pattern.compile(rgx.getRgx());
        Matcher hypernymMatcher = pattern.matcher(line);
        Pattern p2 = Pattern.compile(GeneralBehaviour.NP_RGX);
        hypernymMatcher.find();
        Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
        hyponymMatcher.find();
        //hypernym is the first match of NP in the whole match
        Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
        String hyper = hyponymMatcher.group(1);
        NounPhrase nounPhrase1 = new NounPhrase(hyper);
        NounPhrase nounPhrase = new NounPhrase(hypernym);
        return aggregateNp(hyponymMatcher, nounPhrase1);
    }
}
