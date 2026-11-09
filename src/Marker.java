import java.util.*;

/**
 * Missions 3 and 4. Find a word in a long text.
 */
public class Marker {

    /**
     * Mission 3a. The prefix function of p. pi[i] = the length of the longest proper prefix of p[0..i] that is
     * also a suffix of p[0..i] ("proper" means shorter than p[0..i] itself).
     * Example: p = "ACGAC" gives [0, 0, 0, 1, 2]. The last number is the "Border length" line.
     */
    public static int[] prefixFunction(String p) {
        int[] pi = new int[p.length()];

        int k = 0;

        for (int i = 1; i < p.length(); i++) {
            while (k > 0 && p.charAt(i) != p.charAt(k)) {
                k = pi[k - 1];
            }

            if (p.charAt(i) == p.charAt(k)) {
                k++;
            }

            pi[i] = k;
        }

        return pi;
    }

    /**
     * Mission 3b. KMP. All start positions (counting from 0, in increasing order) where p occurs in text.
     * Overlapping occurrences count: "ACG" occurs in "ACGACG" at 0 and 3, and "AA" in "AAA" at 0 and 1.
     * Use the prefix function: the text pointer never goes back. The number of positions is "Marker hits",
     * the first one is "First hit" (-1 when there is none).
     */
    public static int[] findAll(String text, String p) {
        if (p.length() == 0) {
            return new int[0];
        }

        int[] pi = prefixFunction(p);

        ArrayList<Integer> positions = new ArrayList<>();

        int k = 0;

        for (int i = 0; i < text.length(); i++) {

            while (k > 0 && text.charAt(i) != p.charAt(k)) {
                k = pi[k - 1];
            }

            if (text.charAt(i) == p.charAt(k)) {
                k++;
            }

            if (k == p.length()) {
                positions.add(i - p.length() + 1);

                k = pi[k - 1];
            }
        }

        int[] result = new int[positions.size()];

        for (int i = 0; i < positions.size(); i++) {
            result[i] = positions.get(i);
        }

        return result;
    }

    /**
     * Mission 4. Rabin-Karp. The number of positions i in a such that the word of k letters starting at i
     * (a[i..i+k-1]) also occurs somewhere in b. Count positions, not different words. This is the "Shared words" line.
     * The idea: give every window of k letters a number (a rolling hash, updated in O(1) when the window moves by one),
     * look the number up among the windows of b, and when it is found COMPARE THE LETTERS, because two different words
     * can have the same hash.
     * Example: a = "ACGTAC", b = "TTACGT", k = 4: the words of a are ACGT, CGTA, GTAC; only ACGT is in b, so the answer is 1.
     */
    public static int sharedWords(String a, String b, int k) {
        if (k <= 0 || a.length() < k || b.length() < k) {
            return 0;
        }

        long base = 5;
        long mod = 1000000007L;

        long highest = 1;

        for (int i = 1; i < k; i++) {
            highest = (highest * base) % mod;
        }

        HashMap<Long, ArrayList<Integer>> positions = new HashMap<>();

        long hash = 0;

        for (int i = 0; i < k; i++) {
            hash = (hash * base + value(b.charAt(i))) % mod;
        }

        positions.computeIfAbsent(hash, x -> new ArrayList<>()).add(0);

        for (int i = k; i < b.length(); i++) {
            hash = (hash - value(b.charAt(i - k)) * highest % mod + mod) % mod;

            hash = (hash * base + value(b.charAt(i))) % mod;

            positions.computeIfAbsent(hash, x -> new ArrayList<>()).add(i - k + 1);
        }

        int count = 0;

        hash = 0;

        for (int i = 0; i < k; i++) {
            hash = (hash * base + value(a.charAt(i))) % mod;
        }

        if (containsSameWord(a, 0, b, positions.get(hash), k)) {
            count++;
        }

        for (int i = k; i < a.length(); i++) {
            hash = (hash - value(a.charAt(i - k)) * highest % mod + mod) % mod;

            hash = (hash * base + value(a.charAt(i))) % mod;

            if (containsSameWord(a, i - k + 1, b, positions.get(hash), k)) {
                count++;
            }
        }
        return count;
    }

    private static int value(char c) {
        if (c == 'A') return 1;
        if (c == 'C') return 2;
        if (c == 'G') return 3;
        return 4;
    }

    private static boolean containsSameWord(
        String a,
        int start,
        String b,
        ArrayList<Integer> candidates,
        int k) {

        if (candidates == null) {
            return false;
        }

        for (int pos : candidates) {
            int j = 0;

            while (j < k && a.charAt(start + j) == b.charAt(pos + j)) {
                j++;
            }

            if (j == k) {
                return true;
            }
        }
        return false;
    }
}
