/**
 * The type Fourth.
 * singleton
 */
public final class Fourth extends FirstToFourthRgx {
    /**
     * The Fourth rgx.
     */
    public static final String FOURTH_RGX =
            "<np>([^<]*)</np>\\s(,\\s)?especially\\s(,\\s)?<np>([^<]*)</np>(((,\\s)?(<np>([^<]*)</np>)\\s(,\\s)?)*"
                    + "((((,\\s)?(and\\s)?(or\\s)?)?(<np>([^<]*)</np>))?))?";
    private static Fourth fourthSingleInstance = null;

    /**
     * private constructor.
     */
    private Fourth() { }

    /**
     * Gets instance.
     *
     * @return instance of this
     */
    public static Fourth getInstance() {
        if (fourthSingleInstance == null) {
            fourthSingleInstance = new Fourth();
        }
        return fourthSingleInstance;
    }

    @Override
    protected String getRgx() {
        return FOURTH_RGX;
    }

    @Override
    public NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx) {
        return super.findMatchesInLine(line, Fourth.getInstance());
    }
}
