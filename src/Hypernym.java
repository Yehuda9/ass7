import java.util.Objects;

/**
 * The type Hypernym.
 */
public class Hypernym implements java.lang.Comparable<Hypernym> {
    private String name;


    /**
     * Instantiates a new Hypernym.
     *
     * @param n name
     */
    public Hypernym(String n) {
        this.name = n;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
