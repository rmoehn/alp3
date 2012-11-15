package alp3.ueb;

/**
 * Class for testing the class <code>FeldHeap</code>.
 */
public class FeldHeapTest extends PrioritätsWarteschlangeTest {
    public PrioritätsWarteschlange<Integer, Integer> createPWS() {
        return new FeldHeap<Integer, Integer>();
    }
}
