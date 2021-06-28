/**
 * The type First.
 * singleton
 */
public final class First extends FirstToFourthRgx {
    private static First firstSingleInstance = null;
    /**
     * The First rgx.
     */
    /*public static final String FIRST_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*(\\s*,\\s*)?<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*,\\s*)?(\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";*/
    public static final String FIRST_RGX =
            "<np>([^<]*)</np>(\\s+,\\s+)?\\s+such\\s+as\\s+(\\s*,\\s+)?<np>([^<]*)</np>(((\\s+,\\s+)?(\\s*)(<np>([^<]*)</np>)"
                    + "(\\s+,\\s+)?(\\s*))*((((\\s*,\\s+)?(\\s*and\\s+)?(\\s*or\\s+)?)?(\\s*)(<np>([^<]*)</np>))?))?";

    /**
     * private constructor.
     */
    private First() { }

    /**
     * Gets instance.
     *
     * @return instance of this.
     */
    public static First getInstance() {
        if (firstSingleInstance == null) { firstSingleInstance = new First(); }
        return firstSingleInstance;
    }

    @Override
    protected String getRgx() {
        return FIRST_RGX;
    }

    @Override
    public NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        return super.findMatchesInLine(line, First.getInstance());
    }
}
