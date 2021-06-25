import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FifthRgx extends GeneralBehaviour{

    @Override
    protected NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        NounPhrase nounPhrase = null;
        Matcher hyponymMatcher = null;
        Pattern pattern = Pattern.compile(rgx.getRgx());
        Matcher hypernymMatcher = pattern.matcher(line);
        while (hypernymMatcher.find()) {
            Pattern p2 = Pattern.compile(this.getNP_RGX());
            hyponymMatcher = p2.matcher(hypernymMatcher.group());
            hyponymMatcher.find();
            String hyponym = hyponymMatcher.group();
            hyponymMatcher.find();
            Hypernym hypernym = new Hypernym(hyponymMatcher.group(1));
            hyponymMatcher = p2.matcher(hyponym);
            System.out.println("found hypernym: " + hypernym);
            nounPhrase = new NounPhrase(hypernym);
            //addHypernym(hypernym, hyponymMatcher);
        }
        if (hyponymMatcher == null) {
            return null;
        }
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