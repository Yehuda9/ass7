public class Third extends FirstToFourthRgx {
    private static Third THIRD_SINGLE_INSTANCE = null;
    private final String THIRD_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*including\\s*<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";

    private Third() {}

    public static Third getInstance() {
        if (THIRD_SINGLE_INSTANCE == null) { THIRD_SINGLE_INSTANCE = new Third(); }
        return THIRD_SINGLE_INSTANCE;
    }

    @Override
    public void findMatchesInLine(String line, GeneralBehaviour rgx) {
        super.findMatchesInLine(line, THIRD_RGX);
    }
}
