import java.util.Locale;
import java.util.Objects;

public class Hypernym implements Comparable<Hypernym> {
    private String name;


    public Hypernym(String n) {
        this.name = n;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Hypernym hypernym = (Hypernym) o;
        return Objects.equals(getName().toLowerCase(), hypernym.getName().toLowerCase());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Hypernym hypernym) {
        return getName().toLowerCase().compareTo(hypernym.getName().toLowerCase());
    }
}
