import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private final String SUCH_AS_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
                    + "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";
    private String rgx;
    private Pattern patternSuchAsRgx;
    private Matcher matcher;
    private String currentFind;
    public Regex(String r){
        this.rgx = r;
        this.patternSuchAsRgx = Pattern.compile(rgx);
    }
    public String currentFind(String line){
        this.matcher = patternSuchAsRgx.matcher(line);
        this.currentFind = matcher.group(1);
        nextFind();
        return this.currentFind;
    }
    public Boolean nextFind(){
        return this.matcher.find();
    }
}
