import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBase {
    private RawData rawData;
    private Map<String, List<Hyponym>> db;

    public DataBase(RawData rwdt) {
        this.rawData = rwdt;
        this.db = new TreeMap<>();
    }

    public void findHyponymSuchAs() {
        String rgx = "<np>[^<]*</np>";
        Pattern p = Pattern.compile(rgx);
        int i = 0;
        for (String line: rawData.getLines()) {
            Matcher m = p.matcher(line);
            while (m.find()){
                System.out.println(m.group());
            }
            i++;
            if (i>10){
                break;
            }
        }
    }
}
