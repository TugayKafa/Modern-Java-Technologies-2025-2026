public class UniqueSubstringFinder {
    public static void main(String[] args) {
        System.out.println(longestUniqueSubstring("abcabcbb"));
        System.out.println(longestUniqueSubstring("bbbbb"));
        System.out.println(longestUniqueSubstring("pwwkew"));
        System.out.println(longestUniqueSubstring("abcdefg"));
        System.out.println(longestUniqueSubstring("x"));
        System.out.println(longestUniqueSubstring(""));
    }

    public static String longestUniqueSubstring(String s) {
        if (s.isEmpty()) {
            return s;
        }

        int[] boundsOfLongestSubstring = {0,1};
        extractLongestSubstring(s, boundsOfLongestSubstring);

        return s.substring(boundsOfLongestSubstring[0], boundsOfLongestSubstring[1]);
    }

    private static void extractLongestSubstring(String s, int[] longestBounds) {
        int[] currentBounds = {0,1};
        while (currentBounds[1] < s.length()) {
            char newCharacterToAdd = s.charAt(currentBounds[1]);

            for (int i = currentBounds[0]; i < currentBounds[1]; i++) {
                char charFromSubstring = s.charAt(i);
                if (charFromSubstring == newCharacterToAdd) {
                    getLongestSubstringFromTwoSubstrings(longestBounds, currentBounds);
                    currentBounds[0] = i + 1;
                }
            }

            currentBounds[1]++;
        }

        getLongestSubstringFromTwoSubstrings(longestBounds, currentBounds);
    }

    private static void getLongestSubstringFromTwoSubstrings(int[] longestBounds, int[] currentBounds) {
        if (currentBounds[1] - currentBounds[0] > longestBounds[1] - longestBounds[0]) {
            longestBounds[0] = currentBounds[0];
            longestBounds[1] = currentBounds[1];
        }
    }
}
