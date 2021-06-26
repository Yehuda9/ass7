import java.io.File;
import java.util.Objects;

/**
 * The type Discover hypernym.
 */
public class DiscoverHypernym {
    /**
     * The entry point of application.
     *
     * @param args the input arguments, path to corpus and lemma.
     */
    public static void main(String[] args) {
        Data data = new Data();
        IterateCorpus iterateCorpus = new IterateCorpusSecondPart(data, args[0], args[1]);
        File folder = new File(args[0]);
        File[] listOfFiles = Objects.requireNonNull(folder.listFiles());
        iterateCorpus.sendLineToMatch(listOfFiles);
    }
}
