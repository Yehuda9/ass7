import java.util.Map;

public class Hyponym {
    /*private String name;
    private int count;*/
    private Map<String,Integer> nameCount;

    public Hyponym(Map<String,Integer> nc) {
        this.nameCount = nc;
    }
    public void increase(String name){
        nameCount.put(name, nameCount.get(name) + 1);
    }
}
