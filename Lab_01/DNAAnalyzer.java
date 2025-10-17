public class DNAAnalyzer {
    public static void main(String[] args) {
        System.out.println(longestRepeatingSequence("abracadabra"));
        System.out.println(longestRepeatingSequence("ATACTCGGTACTCT"));
    }

    public static String longestRepeatingSequence(String dna) {
        for (int i = dna.length(); i >= 1; i--) { // length of substring
            for (int j = 0; j <= dna.length() - i - 1; j++) { // substring starts from
                int count = 1;
                String sub = dna.substring(j, j + i);
                for (int k = j + 1; k <= dna.length() - i; k++) { // check for repeating with next substring
                    String curSub = dna.substring(k, k + i);
                    if (sub.equals(curSub)) count++;
                }
                if (count > 1) return sub;
            }
        }
        return "";
    }
}
