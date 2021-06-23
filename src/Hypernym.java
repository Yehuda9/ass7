import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Hypernym {
    private String name;
    //private List<Hyponym> hyponymList;


    public Hypernym(String n) {
        this.name = n;
        //this.hyponymList = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

/*
    public List<Hyponym> getHyponymList() {
        return hyponymList;
    }
*/
    /*public void addHyponym(Hyponym hyponym){
        getHyponymList().add(hyponym);
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Hypernym hypernym = (Hypernym) o;
        return Objects.equals(getName(), hypernym.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return name;
    }
}
