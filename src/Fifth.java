/**
 * The type Fifth.
 * singleton.
 */
public final class Fifth extends FifthRgx {
    /**
     * The Fifth rgx.
     */
    public static final String FIFTH_RGX = "<np>([^<]*)</np>(\\s*,\\s*)?\\s*which\\s+is\\s*(\\s*,\\s*)?"
            + "((\\s*,\\s*)?(\\s*an\\s+example\\s*|\\s*a\\s+kind\\s*|\\s*a\\s+class\\s*)?"
            + "(\\s*,\\s*)?(of\\s+))?<np>([^<]*)</np>";
    private static Fifth fifthSingleInstance = null;

    /**
     * private constructor.
     */
    private Fifth() { }

    /**
     * Gets instance.
     *
     * @return instance of this.
     */
    public static Fifth getInstance() {
        if (fifthSingleInstance == null) { fifthSingleInstance = new Fifth(); }
        return fifthSingleInstance;
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
