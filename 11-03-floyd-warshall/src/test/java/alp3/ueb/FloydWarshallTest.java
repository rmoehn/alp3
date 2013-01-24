package alp3.ueb;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

public class FloydWarshallTest {
    private static final int NOE = FloydWarshall.NO_CONN;
    private static final int[][] edge = {
        { 0   , 30  , NOE , NOE , 100 , 90  },
        { NOE , 0   , 10  , 40  , NOE , NOE },
        { 40  , NOE , 0   , NOE , NOE , 10  },
        { NOE , NOE , NOE , 0   , 30  , NOE },
        { NOE , NOE , NOE , NOE , 0   , NOE },
        { NOE , NOE , NOE , NOE , 20  , 0   }
    };
    private static final int[][] shouldBeDist = {
        { 0   , 30  , 40  , 70  , 70 , 50  },
        { 50  , 0   , 10  , 40  , 40 , 20  },
        { 40  , 70  , 0   , 110 , 30 , 10  },
        { NOE , NOE , NOE , 0   , 30 , NOE },
        { NOE , NOE , NOE , NOE , 0  , NOE },
        { NOE , NOE , NOE , NOE , 20 , 0   }
    };
    private static final int[][][] shouldBePath = {
        { {0},     {1},     {1,2},   {1,3},     {1,2,5,4}, {1,2,5} },
        { {2,0},   {1},     {2},     {3},       {2,5,4},   {2,5}   },
        { {0},     {0,1},   {2},     {0,1,3},   {5,4},     {5}     },
        { {},      {},      {},      {3},       {4},       {}      },
        { {},      {},      {},      {},        {4},       {}      },
        { {},      {},      {},      {},        {4},       {5}     }
    };
    private static final int nodesCnt = edge.length;

    private int[][] distance = new int[nodesCnt][nodesCnt];
    private int[][] between  = new int[nodesCnt][nodesCnt];

    @Before public void setUp() {
        FloydWarshall.findShortestPaths(edge, distance, between);
    }

    // Test whether the shortest distances between nodes are right
    @Test public void testDistances() {
        assertTrue(Arrays.deepEquals(shouldBeDist, distance));
    }

    // Test all shortest paths to be the right paths
    @Test public void testPaths() {
        for (int i = 0; i < nodesCnt; ++i) {
            for (int j = 0; j < nodesCnt; ++j) {
                List<Integer> expectedPath
                    = listFromIntArray(shouldBePath[i][j]);
                List<Integer> calcedPath
                    = FloydWarshall.calcShortestPath(i, j, distance, between);

                assertEquals(expectedPath, calcedPath);
            }
        }
    }

    // Gibt's da nichts von Ratiopharm?
    private static List<Integer> listFromIntArray(int[] ary) {
        List<Integer> res = new LinkedList<>();
        for (int elem : ary) {
            res.add(elem);
        }
        return res;
    }
}
