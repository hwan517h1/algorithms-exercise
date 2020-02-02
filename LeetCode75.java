// Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.
// Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
// A rather straight forward solution is a two-pass algorithm using counting sort.
// First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
// Could you come up with a one-pass algorithm using only constant space?

public class LeetCode75 {
    public static void main(String[] args) {
        int[] input = {2, 1, 1, 0, 2, 1, 1, 0, 0, 1, 2, 0, 0, 1, 1, 2};

        sortColors(input);

        for (int i : input) {
            System.out.print(i + " ");
        }
    }

    public static void sortColors(int[] array) {
        // 3 indices
        int i = 0; // array[i]는 1의 처음으로 0부터 시작함
        int j = 0; // array[j]는 2의 처음으로 0부터 시작함
        int k = array.length - 1; // array[k]는 2의 처음으로 끝부터 시작함

        for (int a = 0; a < array.length; a++) {
            for (int b : array) {
                System.out.print(b + " ");
            }
            System.out.println("(a: " + a + ", i: " + i + ", j: " + j + ", k: " + k + ")");

            // 2는 우선 오른쪽 맨 끝으로 보냄
            if (array[a] == 2) {
                if (j == k) { return; }

                while (array[k] == 2) {
                    if (j < k) { k--; }
                }

                int tmp = array[a];
                array[a] = array[k];
                array[k] = tmp;

                // if (j < k) { k--; }
            }

            if (array[a] == 1) {
                if (j < k) { j++; }
            }

            if (array[a] == 0) {
                int tmp = array[a];
                array[a] = array[i];
                array[i] = tmp;

                if (i < j) { i++; }
                if (j < k) { j++; }
            }
        }
    }
}
