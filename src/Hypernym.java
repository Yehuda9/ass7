import java.util.Objects;

public class Hypernym {
    private String name;


    public Hypernym(String n) {
        this.name = n;
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
        Hypernym hypernym = (Hypernym) o;
        return Objects.equals(getName(), hypernym.getName());
    }

    @Override
    public String toString() {
        return name;
    }
}
