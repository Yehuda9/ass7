public class Second extends FirstToFourthRgx {
    private static Second SECOND_SINGLE_INSTANCE = null;
    public final String SECOND_RGX =
            "such\\s+(<np>([^<]*)</np>)\\s+as(\\s*,\\s*)?\\s+(<np>([^<]*)</np>)((\\s*,\\s*)?(<np>([^<]*)</np>)(\\s*,\\s*)?)*"
                    + "((((\\s*,\\s*)?(\\s*and\\s*)?(\\s*or\\s*)?)(<np>([^<]*)</np>)))?";

    private Second() {}

    public static Second getInstance() {
        if (SECOND_SINGLE_INSTANCE == null) { SECOND_SINGLE_INSTANCE = new Second(); }

        return SECOND_SINGLE_INSTANCE;
    }

    @Override
    protected String getRgx() {
        return SECOND_RGX;
    }

    @Override
    public NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        return super.findMatchesInLine(line, Second.getInstance());
    }
}
