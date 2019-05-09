public class BubbleSort {

    public static void main(String[] args) {
        int[] input = {5, 4, 3, 2, 10, 0, 9, 8, 7, -21, -111, 6, 31, 11, 23, 100, 103, 201, 28, 43, 37, 1, -1};

        sort(input, 1, input.length);

        for (int i : input) {
            System.out.print(i + " ");
        }
    }

    public static void swap(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void sort(int[] array, int p, int r) {
        for (int i = r; i > p; i--) {
            for (int b = p; b < i; b++) {
                if (array[b - 1] > array[b]) {
                    swap(array, b - 1, b);
                }
            }
        }
    }
}
