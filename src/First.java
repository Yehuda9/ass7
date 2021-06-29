/**
 * The type First.
 * singleton
 */
public final class First extends FirstToFourthRgx {
    /**
     * The First rgx.
     */
    public static final String FIRST_RGX =
            "<np>([^<]*)</np>\\s(,\\s)?such(\\s)as\\s(,\\s)?<np>([^<]*)</np>\\s(((,\\s)?(<np>([^<]*)</np>)\\s(,\\s)?)*"
                    + "((((,\\s)?(and\\s)?(or\\s)?)?(<np>([^<]*)</np>))?))?";
    private static First firstSingleInstance = null;

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
        if (firstSingleInstance == null) {
            firstSingleInstance = new First();
        }
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
