import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CreateHypernymDatabase {
    public static void main(String[] args) {
        RawData rawData = new RawData();
        File folder = new File(args[0]);
        File[] listOfFiles = Objects.requireNonNull(folder.listFiles());
        Data data = new Data();
        IterateCorpus iterateCorpus = new IterateCorpus(data);
        iterateCorpus.sendLineToMatch(listOfFiles);
        data.reduceUnder3hyponyms();
        data.sortHyponymList();
        List<Map.Entry<Hypernym, List<Hyponym>>> list = new LinkedList<>(data.getDb().entrySet());
        list.sort(Map.Entry.comparingByKey(Hypernym::compareTo));
        data.setDb(list);
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym:data.getDb().entrySet()) {
            System.out.print(hypernym.getKey()+": ");
            for (Hyponym hyponym: hypernym.getValue()) {
                System.out.print(hyponym+", ");
            }
            System.out.println("");
        }
        /*DataBase dataBase = new DataBase();
        for (File file : listOfFiles) {
            rawData = new RawData();
            try {
                BufferedReader input = new BufferedReader(new FileReader(file));
                rawData.updateDb(input);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
            dataBase.findMatches(rawData);

            //break;
        }
        List<Map.Entry<Hypernym, List<Hyponym>>> list = new LinkedList<>(dataBase.getDb().entrySet());
        list.sort(Map.Entry.comparingByKey(Hypernym::compareTo));
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym:list) {
            dataBase.getDb().put(hypernym.getKey(),hypernym.getValue());
        }
        dataBase.reduceUnder3hyponyms(list);
        dataBase.sortHyponymList(list);
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym:list) {
            System.out.print(hypernym.getKey());
            for (Hyponym hyponym: hypernym.getValue()) {
                System.out.print(hyponym+", ");
            }
            System.out.println("");
        }
        //List<Map.Entry<Hypernym, Hyponym>> list1 = dataBase.getDb().entrySet().;
        //List<Map.Entry<Hypernym, Hyponym>> list = new ArrayList<>(dataBase.getDb().entrySet());
        //list.sort(Entry.comparingByValue());
        *//*dataBase.getDb().entrySet().stream()
                .sorted((k1, k2) -> k1.getKey().compareTo(k2.getKey()))
                .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue()));*//*
        for (Map.Entry<Hypernym, List<Hyponym>> hypernym:dataBase.getDb().entrySet()) {
            //System.out.println(hypernym);
        }
        *//*DataBase dataBase = new DataBase(rawData);
        dataBase.findMatches();*//*
        //dataBase.findMatches();
        *//*for (Map.Entry<Hypernym, List<Hyponym>> hypernym:dataBase.getDb().entrySet()) {
            System.out.println(hypernym);
        }*/
    }
}
