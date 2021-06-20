import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CreateHypernymDatabase {
    public static void main(String[] args) {
        RawData rawData = null;
        try {
            BufferedReader input = new BufferedReader(new FileReader(args[0] + "mbta.com_mtu.pages_0.possf2"));
            rawData = new RawData(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        DataBase dataBase = new DataBase(rawData);
        dataBase.findHyponymSuchAs();
    }
}
