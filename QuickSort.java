public class QuickSort {

    public static void main(String[] args) {
        int[] input = {5, 4, 3, 2, 10, 0, 9, 8, 7, -21, -111, 6, 31, 11, 23, 100, 103, 201, 28, 43, 37, 1, -1};

        sort(input, 0, input.length - 1);

        for (int i : input) {
            System.out.print(i + " ");
        }
    }

    public static void swap(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void sort(int array[], int left, int right) {
        if (left < right) {
            // 방법이 다양함
            int pivotIndex = partition1(array, left, right);
            // int pivotIndex = partition2(array, left, right);

            sort(array, left, pivotIndex - 1);
            sort(array, pivotIndex + 1, right);
        }
    }

    // 방법 1: Pivot을 오른쪽 맨 끝으로 정하는 경우(기준 2개는 왼쪽 끝에서 시작함)
    private static int partition1(int array[], int left, int right) {
        int pivot = array[right];
        int i = left;
        int j = left;
        // System.out.println("(Left: " + array[left] + ", Right: " + array[right] + ", Pivot: " + pivot + ")");

        while (j < right) {
            // array[i]: Pivot보다 같거나 큰 수 중 맨 앞임
            // arrya[j]: 아직 대소비교를 하지 않은 수 중 맨 앞임
            // Pivot보다 작은 영역은 i보다 앞임
            if (array[j] < pivot) {
                swap(array, i, j);

                i++;
                j++;
            } else { // array[j] >= pivot
                j++;
            }
        }

        swap(array, i, right);

        return i;
    }

    // 방법 2: Pivot을 중간으로 정하는 경우(기준 2개는 양쪽 끝에서 시작하여 만날 때까지 진행됨)
    private static int partition2(int array[], int left, int right) {
        int pivot = array[(left + right) / 2];
        // System.out.println("(Left: " + array[left] + ", Right: " + array[right] + ", Pivot: " + pivot + ")");

        while (left < right) {
            while ((array[left] < pivot) && (left < right)) { left++; }
            while ((array[right] > pivot) && (left < right)) { right--; }

            if (left < right) { swap(array, left, right); }
        }

        return left;
    }
}
