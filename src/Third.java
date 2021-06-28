/**
 * The type Third.
 * singleton
 */
public final class Third extends FirstToFourthRgx {
    private static Third thirdSingleInstance = null;
    /*private static final String THIRD_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*including\\s*(\\s*,\\s*)?<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";*/
    private static final String THIRD_RGX =
            "<np>([^<]*)</np>(\\s+,\\s*)?\\s+including\\s+(\\s*,\\s+)?(\\s*)<np>([^<]*)</np>(((\\s+,\\s+)?(\\s*)(<np>([^<]*)</np>)(\\s*)"
                    + "(\\s*,\\s+)?)*((((\\s*and\\s+)?(\\s*or\\s+)?)?(\\s*)(<np>([^<]*)</np>))?))?";

    /**
     * private constructor.
     */
    private Third() { }

    /**
     * Gets instance.
     *
     * @return instance of this.
     */
    public static Third getInstance() {
        if (thirdSingleInstance == null) { thirdSingleInstance = new Third(); }
        return thirdSingleInstance;
    }

    @Override
    protected String getRgx() {
        return THIRD_RGX;
    }

    @Override
    public NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        return super.findMatchesInLine(line, Third.getInstance());
    }
}
