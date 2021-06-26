public abstract class GeneralBehaviour {
    private final String NP_RGX = "<np>([^<]*)</np>";

    protected String getNP_RGX() {
        return NP_RGX;
    }
    protected abstract String getRgx();
    protected abstract NounPhrase findMatchesInLine(String line, GeneralBehaviour rgx);
}
