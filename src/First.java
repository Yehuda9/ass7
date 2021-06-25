public class First extends FirstToFourthRgx {
    public final String FIRST_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";
    private static First FIRST_SINGLE_INSTANCE = null;

    private First(){}

    @Override
    protected String getRgx() {
        return FIRST_RGX;
    }

    @Override
    public NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        return super.findMatchesInLine(line, First.getInstance());
    }
    public static First getInstance()
    {
        if (FIRST_SINGLE_INSTANCE == null)
            FIRST_SINGLE_INSTANCE = new First();

        return FIRST_SINGLE_INSTANCE;
    }
}
