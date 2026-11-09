import java.util.*;

/**
 * Mission 5. Dynamic programming on the tree of infections. Root the tree in person 0.
 * Input.Tree.neighbours() gives the neighbours of every person. The number of people is at most about 100, so a recursive DFS is fine here.
 */
public class Outbreak {

    /**
     * Choose people to test so that for every link (somebody infected somebody) at least one of the two is tested,
     * and the total cost of testing is as small as possible. cost[v] is the price of testing person v.
     * Returns tested[v] = true if person v is tested.
     *
     * State for every person v (looking only at v and everything below it): the smallest cost when v IS tested, and
     * the smallest cost when v is NOT tested. What must then be true about the children of v in each case?
     * Do not forget to rebuild the answer (who exactly is tested), not only the cost: the picture needs it.
     * Main calls this twice: with all costs equal to 1 (the line "Tests needed") and with the real costs ("Cheapest testing").
     */
    public static boolean[] cheapestTests(int n, int[][] nb, int[] cost) {
        long[][] dp = new long[n][2];

        dfsCost(0, -1, nb, cost, dp);

        boolean[] tested = new boolean[n];

        rebuild(0, -1, true, true, nb, dp, tested);

        return tested;
    }

    private static void dfsCost(int v, int parent, int[][] nb,
                                int[] cost, long[][] dp) {

        dp[v][0] = cost[v];
        dp[v][1] = 0;

        for (int child : nb[v]) {
            if (child == parent) {
                continue;
            }

            dfsCost(child, v, nb, cost, dp);

            dp[v][0] += Math.min(dp[child][0], dp[child][1]);

            dp[v][1] += dp[child][0];
        }
    }

    private static void rebuild(int v, int parent,
                               boolean parentTested, boolean isRoot,
                               int[][] nb, long[][] dp,
                               boolean[] tested) {

        boolean chooseTested;

        if (!isRoot && !parentTested) {
            chooseTested = true;
        } else {
            chooseTested = dp[v][0] <= dp[v][1];
        }

        tested[v] = chooseTested;

        for (int child : nb[v]) {
            if (child != parent) {
                rebuild(child, v, chooseTested, false, nb, dp, tested);
            }
        }
    }

    /**
     * The number of links on the longest chain of infections, that is the longest path in the tree (counted in links).
     * A path goes up from one person and down to another; for every person you need the two longest ways down.
     * Example: a line 0-1-2-3 has chain 3; a star with centre 0 and leaves 1, 2, 3 has chain 2.
     */
    public static int longestChain(int n, int[][] nb) {
        int[] best = new int[1];

        depth(0, -1, nb, best);

        return best[0];
    }

    private static int depth(int v, int parent,
                             int[][] nb, int[] best) {

        int first = 0;
        int second = 0;

        for (int child : nb[v]) {
            if (child == parent) {
                continue;
            }

            int d = depth(child, v, nb, best);

            if (d > first) {
                second = first;
                first = d;
            } else if (d > second) {
                second = d;
            }
        }

        best[0] = Math.max(best[0], first + second);

        return first + 1;
    }
}
