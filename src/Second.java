/**
 * The type Second.
 * singleton
 */
public final class Second extends FirstToFourthRgx {
    private static Second secondSingleInstance = null;
    /**
     * The Second rgx.
     */
    public static final String SECOND_RGX =
            "such\\s+(<np>([^<]*)</np>)\\s+as(\\s*,\\s*)?\\s+(<np>([^<]*)</np>)((\\s*,\\s*)?"
                    + "(<np>([^<]*)</np>)(\\s*,\\s*)?)*"
                    + "((((\\s*,\\s*)?(\\s*and\\s*)?(\\s*or\\s*)?)(<np>([^<]*)</np>)))?";

    /**
     * private constructor.
     */
    private Second() { }

    /**
     * Gets instance.
     *
     * @return instance of this
     */
    public static Second getInstance() {
        if (secondSingleInstance == null) { secondSingleInstance = new Second(); }
        return secondSingleInstance;
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
