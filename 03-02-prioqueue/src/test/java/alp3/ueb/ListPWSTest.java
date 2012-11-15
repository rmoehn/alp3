package alp3.ueb;

/**
 * Class for testing the class <code>ListPWS</code>.
 */
public class ListPWSTest extends PrioritätsWarteschlangeTest {
    public PrioritätsWarteschlange<Integer, Integer> createPWS() {
        return new ListPWS<Integer, Integer>();
    }
}
