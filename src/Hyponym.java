import java.util.Objects;

public class Hyponym {
    private String name;
    private int count;


    public Hyponym(String n, int c) {
        this.name = n;
        this.count = c;
    }

    public int getCount() {
        return count;
    }

    public void increase() {
        this.count += 1;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Hyponym hyponym = (Hyponym) o;
        return Objects.equals(getName(), hyponym.getName());
    }

    @Override
    public String toString() {
        return name + " (" + count + ")";
    }
}
