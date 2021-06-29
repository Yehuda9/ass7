import java.util.Objects;

/**
 * The type Hyponym.
 */
public class Hyponym implements java.lang.Comparable<Hyponym> {
    private String name;
    private int count;


    /**
     * Instantiates a new Hyponym.
     *
     * @param n name
     * @param c count
     */
    public Hyponym(String n, int c) {
        this.name = n;
        this.count = c;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Increase.
     */
    public void increase() {
        this.count += 1;
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
        return Objects.hash(getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hyponym hyponym = (Hyponym) o;
        return Objects.equals(getName().toLowerCase(), hyponym.getName().toLowerCase());
    }

    @Override
    public String toString() {
        return name + " (" + count + ")";
    }

    @Override
    public int compareTo(Hyponym hyponym) {
        return hyponym.getCount() - getCount();
    }
}
