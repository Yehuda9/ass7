public class Fifth extends FifthRgx {
    private static Fifth FIFTH_SINGLE_INSTANCE = null;
    public final String FIFTH_RGX = "<np>([^<]*)</np>(\\s*,\\s*)?\\s*which\\s+is\\s*(\\s*,\\s*)?"
            + "((\\s*,\\s*)?(\\s*an\\s+example\\s*|\\s*a\\s+kind\\s*|\\s*a\\s+class\\s*)?(\\s*,\\s*)?(of\\s+))?<np>([^<]*)</np>";

    private Fifth() {}

    public static Fifth getInstance() {
        if (FIFTH_SINGLE_INSTANCE == null) { FIFTH_SINGLE_INSTANCE = new Fifth(); }

        return FIFTH_SINGLE_INSTANCE;
    }
    @Override
    public String getRgx() {
        return FIFTH_RGX;
    }

    @Override
    public NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        return super.findMatchesInLine(line, Fifth.getInstance());
    }
}
