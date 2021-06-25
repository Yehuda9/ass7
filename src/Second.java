public class Second extends FirstToFourthRgx {
    private static Second SECOND_SINGLE_INSTANCE = null;
    private final String SECOND_RGX =
            "such\\s+(<np>([^<]*)</np>)\\s+as\\s+(<np>([^<]*)</np>)((\\s*,\\s*)?(<np>([^<]*)</np>))*"
                    + "(((\\s*,\\s*|\\s*and\\s*|\\s*or\\s*)(<np>([^<]*)</np>)))?";

    private Second() {}

    public static Second getInstance() {
        if (SECOND_SINGLE_INSTANCE == null) { SECOND_SINGLE_INSTANCE = new Second(); }

        return SECOND_SINGLE_INSTANCE;
    }

    @Override
    public void findMatchesInLine(String line, GeneralBehaviour rgx) {
        super.findMatchesInLine(line, SECOND_RGX);
    }
}
