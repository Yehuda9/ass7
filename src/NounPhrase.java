import java.util.LinkedList;
import java.util.List;

public class NounPhrase {
    private Hypernym hypernym;
    private List<Hyponym> hyponymList;
    public NounPhrase(Hypernym hyper,List<Hyponym> hypo){
        this.hypernym = hyper;
        this.hyponymList = hypo;
    }
    public NounPhrase(Hypernym hyper){
        this(hyper,new LinkedList<>());
    }
    public Hypernym getHypernym() {
        return hypernym;
    }

    public List<Hyponym> getHyponymList() {
        return hyponymList;
    }
    public void add(Hyponym hyponym){
        hyponymList.add(hyponym);
    }
}
