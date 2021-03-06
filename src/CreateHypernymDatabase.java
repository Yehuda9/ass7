import java.io.File;
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
        IterateCorpus iterateCorpus = new IterateCorpusFirstPart(data, args[1]);
        iterateCorpus.sendLineToMatch(listOfFiles);
    }
}
