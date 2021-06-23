import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindHypernymInText {
    private final String SUCH_AS_RGX =
            "<np>([^<]*)</np>(\\s*,\\s*)?\\s*such\\s+as\\s*<np>([^<]*)</np>((\\s*,\\s*)<np>([^<]*)</np>)*"
                    + "((\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?";
    private final String SUCH_NP_AS_RGX =
            "such\\s+(<np>([^<]*)</np>)\\s+as\\s+(<np>([^<]*)</np>)((\\s*,\\s*)(<np>([^<]*)</np>)"
                    + "((\\s*,\\s*\\s*and\\s*|\\s*or\\s*)<np>([^<]*)</np>)?)*";
    //private final String NP = "<np>([^<]*)</np>";
    private Pattern pattern1;
    private Pattern pattern2;
    private Matcher matcher1;
    private Matcher matcher2;
    private String line;

    public FindHypernymInText(String l) {
        this.line = l;
        this.pattern1 = Pattern.compile(SUCH_AS_RGX);
        this.pattern2 = Pattern.compile(SUCH_NP_AS_RGX);
        this.matcher1 = pattern1.matcher(line);
        this.matcher2 = pattern2.matcher(line);
    }

    public List<String> matchList() {
        List<String> stringList = new LinkedList<>();
        while (isFind()) {
            if (matcher1.find()){
                stringList.add(matcher1.group(1));
            }
            if (matcher2.find()){
                stringList.add(matcher2.group(1));
            }
        }
        return stringList;
    }

    private boolean isFind() {
        return matcher1.find() || matcher2.find();
    }
}
