public class HeapSort {

    public static void main(String[] args) {
        int[] input = {5, 4, 3, 2, 10, 0, 9, 8, 7, -21, -111, 6, 31, 11, 23, 100, 103, 201, 28, 43, 37, 1, -1};

        sort(input);

        for (int i : input) {
            System.out.print(i + " ");
        }
    }

    public static void swap(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    public static void sort(int[] array) {
        int n = array.length;

        // 초기화
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i); // i는 부모 노드
        }

//        // 최소힙에서 추출(내림차순)
//        for (int i = n - 1; i > 0; i--) {
//            swap(array, 0, i);
//            heapify(array, i, 0);
//        }

        // 최대힙에서 추출(오름차순)
        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i);
            heapify(array, i, 0);
        }
    }

    public static void heapify(int array[], int n, int i) {
        int p = i; // 부모 노드
        int l = i * 2 + 1; // 왼쪽 자식 노드
        int r = i * 2 + 2; // 오른쪽 자식 노드

//        // 최소힙
//        if ((l < n) && (array[p] > array[l])) { p = l; }
//        if ((r < n) && (array[p] > array[r])) { p = r; }

        // 최대힙
        if ((l < n) && (array[p] < array[l])) { p = l; }
        if ((r < n) && (array[p] < array[r])) { p = r; }

        if (i != p) {
            swap(array, p, i);
            heapify(array, n, p);
        }
    }
}






