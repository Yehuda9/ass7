import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CreateHypernymDatabase {
    public static void main(String[] args) {
        File folder = new File(args[0]);
        File[] listOfFiles = Objects.requireNonNull(folder.listFiles());
        Data data = new Data();
        IterateCorpus iterateCorpus = new IterateCorpusFirstPart(data,args[1]);
        iterateCorpus.sendLineToMatch(listOfFiles);
        List<Map.Entry<Hypernym, List<Hyponym>>> list = new LinkedList<>(data.getDb().entrySet());
        list.sort(Map.Entry.comparingByKey(Hypernym::compareTo));
    }
}
