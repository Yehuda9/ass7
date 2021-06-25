public abstract class GeneralBehaviour {
    private final String NP_RGX = "<np>([^<]*)</np>";

    protected String getNP_RGX() {
        return NP_RGX;
    }
    protected abstract String getRgx();

    protected abstract NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx);

    /*protected void addHypernym(Hypernym hypernym, Matcher matcher) {
        if (!data.containHypernym(hypernym)) {
            data.addHypernym(hypernym, new LinkedList<>());
        }
        while (matcher.find()) {
            Hyponym hyponym = new Hyponym(matcher.group(1), 1);
            addHyponym(hypernym, hyponym);
        }
    }

    protected void addHyponym(Hypernym hypernym, Hyponym hyponym) {
        if (data.isHypernymContainHyponym(hypernym, hyponym)) {
            data.increaseHyponym(hypernym,hyponym);
            System.out.println("found hyponym: " + db.get(hypernym).get(db.get(hypernym).indexOf(hyponym)));
        } else {
            data.addHyponym(hypernym,hyponym);
            System.out.println("found hyponym: " + hyponym);
        }
    }*/
}
