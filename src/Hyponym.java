import java.util.Objects;

public class Hyponym {
    private String name;
    //private int count;


    public Hyponym(String n) {
        this.name = n;
        //this.count = c;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Hyponym hyponym = (Hyponym) o;
        return Objects.equals(getName(), hyponym.getName());
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
