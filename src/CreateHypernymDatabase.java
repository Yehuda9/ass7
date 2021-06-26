import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Create hypernym database.
 */
public class CreateHypernymDatabase {
    /**
     * The entry point of application.
     *
     * @param args the input arguments, path to corpus and path to txt file result
     */
    public static void main(String[] args) {
        //create array of files in corpus folder
        File folder = new File(args[0]);
        File[] listOfFiles = Objects.requireNonNull(folder.listFiles());
        Data data = new Data();
        IterateCorpus iterateCorpus = new IterateCorpusFirstPart(data,args[1]);
        iterateCorpus.sendLineToMatch(listOfFiles);
        /*List<Map.Entry<Hypernym, List<Hyponym>>> list = new LinkedList<>(data.getDb().entrySet());
        list.sort(Map.Entry.comparingByKey(Hypernym::compareTo));*/
    }
}
