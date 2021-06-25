public class Fourth extends FirstToFourthRgx {
    private final String FOURTH_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*especially\\s*<np>([^<]*)</np>(((\\s*,\\s*)?(<np>([^<]*)</np>)"
                    + "(\\s*,\\s*)?)*((((\\s*and\\s*)?(\\s*or\\s*)?)?(<np>([^<]*)</np>))?))?";
    private static Fourth FOURTH_SINGLE_INSTANCE = null;

    private Fourth() {}



    @Override
    public void findMatchesInLine(String line, GeneralBehaviour rgx) {
        super.findMatchesInLine(line, FOURTH_RGX);
    }
    public static Fourth getInstance() {
        if (FOURTH_SINGLE_INSTANCE == null) { FOURTH_SINGLE_INSTANCE = new Fourth(); }
        return FOURTH_SINGLE_INSTANCE;
    }
}
