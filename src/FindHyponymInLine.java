import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindHyponymInLine {
    private final String NP = "<np>([^<]*)</np>";
    String line;
    private Pattern pattern1;
    private Matcher matcher1;

    public FindHyponymInLine(String l) {
        this.line = l;
        this.pattern1 = Pattern.compile(NP);
        this.matcher1 = pattern1.matcher(line);
    }

    public List<String> matchList() {
        List<String> stringList = new LinkedList<>();
        while (isFind()) {
            stringList.add(matcher1.group());
        }
        return stringList;
    }

    private boolean isFind() {
        return matcher1.find();
    }
}
