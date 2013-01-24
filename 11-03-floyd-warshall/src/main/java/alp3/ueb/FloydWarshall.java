package alp3.ueb;

import java.util.Collections;
import java.util.List;
import java.util.LinkedList;

public class FloydWarshall {
    public static final int NO_CONN = Integer.MAX_VALUE;

    /**
     * Finds the shortest paths between nodes in {@code edge}.
     *
     * @param edge an array of arrays {@code int}s representing the the
     * lengths of edges between two nodes
     * @param distance an empty array of arrays {@code int}s of the the same
     * size as edge
     * @param between an empty array of arrays {@code int}s of the the same
     * size as edge
     */
    public static void findShortestPaths(int[][] edge, int[][] distance,
                                         int[][] between) {
        assert(edge.length == edge[0].length);
        assert(edge.length == distance.length);
        assert(edge.length == distance[0].length);
        assert(edge.length == between.length);
        assert(edge.length == between[0].length);

        int nodesCnt = edge.length;

        // Initialise the current shortest distances to the edge lengths
        for (int i = 0; i < nodesCnt; ++i) {
            for (int j = 0; j < nodesCnt; ++j) {
                distance[i][j] = edge[i][j];
                between[i][j]  = NO_CONN;
            }
        }

        // Find paths from i to j, using at most maxNode
        for (int maxNode = 0; maxNode < nodesCnt; ++maxNode) {
            for (int i = 0; i < nodesCnt; ++i) {
                for (int j = 0; j < nodesCnt; ++j) {
                    // If there is a shorter path using node maxNode, too
                    int possLen = distance[i][maxNode] + distance[maxNode][j];
                    if (distance[i][maxNode] != NO_CONN
                            && distance[maxNode][j] != NO_CONN
                            && possLen < distance[i][j]) {
                        // Use this as the current shortest distance
                        distance[i][j] = possLen;

                        // From i to j, one should go via maxNode
                        between[i][j] = maxNode;
                    }
                }
            }
        }
    }

    /**
     * Calculates the shortest path (not including start node) between two
     * nodes using information from the {@code findShortestPaths} method.
     *
     * @param a the start node
     * @param b the end node
     * @param dist the distance matrix
     * @param between the array of arrays of {@code int}s as it is after the
     * run of {@code findShortestPaths}
     */
    public static List<Integer> calcShortestPath(int a, int b, int[][] dist,
                                                 int[][] between) {
        List<Integer> path = new LinkedList<>();

        // Return an empty list if there is no path between the given nodes
        if (dist[a][b] == NO_CONN) {
            return Collections.emptyList();
        }

        // If there is no in-between node to be used
        if (between[a][b] == NO_CONN) {
            path.add(b);
        }
        else {
            // Use the path from a to the in-between node to b
            path.addAll( calcShortestPath(a, between[a][b], dist, between) );
            path.addAll( calcShortestPath(between[a][b], b, dist, between) );
        }

        return path;
    }
}
