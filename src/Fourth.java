public class Fourth extends FirstToFourthRgx {
    public final String FOURTH_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*especially\\s*<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";
    private static Fourth FOURTH_SINGLE_INSTANCE = null;

    private Fourth() {}


    @Override
    protected String getRgx() {
       return FOURTH_RGX;
    }

    @Override
    public NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        return super.findMatchesInLine(line, Fourth.getInstance());
    }
    public static Fourth getInstance() {
        if (FOURTH_SINGLE_INSTANCE == null) { FOURTH_SINGLE_INSTANCE = new Fourth(); }
        return FOURTH_SINGLE_INSTANCE;
    }
}
