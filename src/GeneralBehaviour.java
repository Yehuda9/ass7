import java.util.regex.Matcher;

/**
 * The type General behaviour.
 * define shared activities for all connections between hypernyms and hyponyms
 */
public abstract class GeneralBehaviour {
    public static final String NP_RGX = "<np>([^<]*)</np>";

    /**
     * Gets rgx.
     *
     * @return the rgx
     */
    protected abstract String getRgx();

    /**
     * Find matches in line.
     * given one line find its first match of hypernym, and all its hyponyms.
     *
     * @param line line
     * @param rgx  rgx
     * @return noun phrase
     */
    protected abstract NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx);

    /**
     * Aggregate noun phrase.
     * given hypernym and a matcher, find its hyponyms.
     *
     * @param matcher    NP matcher
     * @param nounPhrase noun phrase
     * @return noun phrase
     */
    protected NounPhrase aggregateNp(Matcher matcher, NounPhrase nounPhrase) {
        while (matcher.find()) {
            Hyponym hyponym = new Hyponym(matcher.group(1), 1);
            nounPhrase.add(hyponym);
        }
        return nounPhrase;
    }
}
