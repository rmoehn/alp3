package alp3.ueb;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import org.apache.commons.collections.list.SetUniqueList;

public class QuickselectUtils {
    // Not enough time to write a proper utility class
    private static List<Integer> randomList(int size) {
        Random random            = new Random();
        List<Integer> randomList = new ArrayList<Integer>(size);

        for (int i = 0; i < size; ++i) {
            randomList.add(random.nextInt(9999));
        }

        return randomList;
    }

    /**
     * Returns a list of unique random integers. Note that the final size need
     * not be the one specified, as making the list unique may consume
     * elements.
     */
    public static List<Integer> uniqueRandomList(int size) {
        // SetUniqueList implements the List interface and randomList()
        // returns a List<Integer>. Thus the conversion is safe.
        @SuppressWarnings("unchecked")
        List<Integer> ul = SetUniqueList.decorate(randomList(size));
        return ul;
    }
}
