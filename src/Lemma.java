import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Lemma {
    private First first = First.getInstance();
    private Second second = Second.getInstance();
    private Third third = Third.getInstance();
    private Fourth fourth = Fourth.getInstance();
    private Fifth fifth = Fifth.getInstance();
    private List<GeneralBehaviour> RgxList = new LinkedList<>(Arrays.asList(first, second, third, fourth, fifth));

}
