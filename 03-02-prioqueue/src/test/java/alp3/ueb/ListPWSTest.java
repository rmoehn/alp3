package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Class for testing the class <code>ListPWS</code>.
 */
public class ListPWSTest extends PrioritätsWarteschlangeTest {
    public ListPWS<Integer, Integer> createPWS() {
        return new ListPWS<Integer, Integer>();
    }
}
