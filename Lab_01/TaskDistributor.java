import java.util.Arrays;

public class TaskDistributor {
    public static void main(String[] args) {
        System.out.println(minDifference(new int[]{1, 2, 3, 4, 5}));
        System.out.println(minDifference(new int[]{10, 20, 15, 5}));
        System.out.println(minDifference(new int[]{7, 3, 2, 1, 5, 4}));
        System.out.println(minDifference(new int[]{9, 1, 1, 1}));
        System.out.println(minDifference(new int[]{}));
        System.out.println(minDifference(new int[]{120}));
        System.out.println(minDifference(new int[]{30, 30}));
    }

    public static int minDifference(int[] tasks) {
        int left = 0, right = 0;
        boolean flag = false;

        Arrays.sort(tasks);

        for (int i = tasks.length - 1; i >= 0; i--) {
            if (flag) {
                left += tasks[i];
            } else {
                right += tasks[i];
            }
            flag = left <= right;
        }

        return Math.abs(right - left);
    }
}
