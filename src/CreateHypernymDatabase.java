import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CreateHypernymDatabase {
    public static void main(String[] args) {
        RawData rawData = new RawData();
        File folder = new File(args[0]);
        File[] listOfFiles = Objects.requireNonNull(folder.listFiles());
        DataBase dataBase = new DataBase();
        for (File file : listOfFiles) {
            try {
                BufferedReader input = new BufferedReader(new FileReader(file));
                rawData.updateDb(input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
            dataBase.findMatches(rawData);
            for (Map.Entry<Hypernym, List<Hyponym>> hypernym:dataBase.getDb().entrySet()) {
                System.out.println(hypernym);
            }
            //break;
        }
        /*DataBase dataBase = new DataBase(rawData);
        dataBase.findMatches();*/
        //dataBase.findMatches();
        /*for (Map.Entry<Hypernym, List<Hyponym>> hypernym:dataBase.getDb().entrySet()) {
            System.out.println(hypernym);
        }*/
    }
}
