import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public class CreateHypernymDatabase {
    public static void main(String[] args) {
        RawData rawData = null;
        File folder = new File(args[0]);
        File[] listOfFiles = Objects.requireNonNull(folder.listFiles());
        for (File file:listOfFiles) {
            try {
                BufferedReader input = new BufferedReader(new FileReader(file));
                rawData = new RawData(input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
            break;
        }
        DataBase dataBase = new DataBase(rawData);
        dataBase.findHyponymSuchAs();
    }
}
