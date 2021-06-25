import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FirstToFourthRgx extends GeneralBehaviour {
    public FirstToFourthRgx() {}

    protected NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        Pattern pattern = Pattern.compile(rgx.getRgx());
        Matcher hypernymMatcher = pattern.matcher(line);
        Pattern p2 = Pattern.compile(this.getNP_RGX());
        hypernymMatcher.find();
        Matcher hyponymMatcher = p2.matcher(hypernymMatcher.group());
        hyponymMatcher.find();
        //hypernym is the first match of NP in the whole match
        Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
        System.out.println("found hypernym: " + hypernym);
        NounPhrase nounPhrase = new NounPhrase(hypernym);
        //addHypernym(hypernym, hyponymMatcher);
        return aggregateNp(hyponymMatcher, nounPhrase);
    }

    protected NounPhrase aggregateNp(Matcher matcher, NounPhrase nounPhrase) {
        while (matcher.find()) {
            Hyponym hyponym = new Hyponym(matcher.group(1), 1);
            nounPhrase.add(hyponym);
        }
        return nounPhrase;
    }
}
