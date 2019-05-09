// 최악의 경우에도 선형 시간을 보장하는 선택 알고리즘(The linear-time selection algorithm in the worst case) 
public class WorstCaseLinearTimeSelection {
    
    public static void main(String args[]) {
        int[] a = { 
            10, 9, 8, 7, 6, 5, 4, 3, 2, 1,
            20, 19, 18, 17, 16, 15, 14, 13, 12, 11,
            30, 29, 28, 27, 26, 25, 24, 23, 22, 21,
            40, 39, 38, 37, 36, 35, 34, 33, 32, 31,
            50, 49, 48, 47, 46, 45, 44, 43, 42, 41,
            60, 59, 58, 57, 56, 55, 54, 53, 52, 51,
            70, 69, 68, 67, 66, 65, 64, 63, 62, 61,
            80, 79, 78, 77, 76, 75, 74, 73, 72, 71,
            90, 89, 88, 87, 86, 85, 84, 83, 82, 81,
            100, 99, 98, 97, 96, 95, 94, 93, 92, 91
        };
        
        int result1= select(a, 1, a.length, 10);
        int result2= select(a, 1, a.length, 33);
        int result3= select(a, 1, a.length, a.length / 2);
        int result4= select(a, 1, a.length, 79);
        int result5= select(a, 1, a.length, 100);

        System.out.println("1 이상 100 이하 수");
        System.out.println("10번째 수: " + result1);
        System.out.println("33번째 수: " + result2);
        System.out.println("50번째 수: " + result3);
        System.out.println("79번째 수: " + result4);
        System.out.println("100번째 수: " + result5);
    }

    private static int getMedian(int[] a, int p, int r) {
        a = insertionSort(a, p, r);
        int half = (r - p) / 2;

        return a[p - 1 + half];
    }

    private static int medianPartition(int[] a, int p, int r, int pivot) {
        int pivotIndex = getIndex(a, pivot);

        swap(a, pivotIndex , r);

        return partition(a, p, r);
    }

    private static int getIndex(int[] a, int value) {
        int index = 0;

        for(int i = 0; i < a.length; i++) {
            if(value == a[i]) { index =  i + 1; }
        }

        return index;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = tmp;
    }

    private static int partition(int[] a, int p, int r) {
        int pivot = a[r - 1];
        int i = p;
        int j = p;

        while(j < r) {
            if(a[j - 1] < pivot) {
                swap(a, i, j);
                i++;
                j++;
            } else {
                j++;
            }
        }

        swap(a, i, r);

        return i;
    }

    private static int[] insertionSort(int[] a, int p, int r) {
        for(int i = p + 1; i < r + 1; i++) {
            int sorted = i - 1;
            int tmp = a[i - 1];

            while(sorted >= p && tmp < a[sorted - 1]) {
                a[sorted] = a[sorted - 1];
                sorted--;
            }
            a[sorted] = tmp;
        }

        return a;
    }
 
    // The meaning of k is related to the ENTIRE array
    public static int select(int[] a, int p, int r, int k) {
        if ((r - p + 1) <= 5) {
            a = insertionSort(a, p, r);

            return a[k - 1];
        }

        int groups = (r - p + 1) / 5;
        int[] medians;
        if((r - p + 1) % 5 == 0) { medians = new int[groups]; } // only complete groups
        else { medians = new int[groups + 1]; } // a incomplete group

        for(int i = 0; i < groups; i++) { // for complete groups
            medians[i] = getMedian(a, p + 5 * i, p + 5 * i + 4);
        }
        if(p + 5 * groups <= r) { // only for a incomplete group
            medians[groups] = getMedian(a, p + 5 * groups, r);
        }

        // Medians의 median을 찾는 것은 기존 문제와 다른 완전히 새로운 문제라고 볼 수 있음, 하지만 같은 방식이므로 함수를 이용하는 것 
        int pivot = select(medians, 1, medians.length, (medians.length + 1) / 2);
        int pivotIndex = medianPartition(a, p, r, pivot);  // Pivot을 기준으로 분할하는 것은 linear-time complexity임

        // 적절한 pivot을 기준으로 k번째 수가 있는 범위를 지속적으로 줄여나가는 방식
        if (k < pivotIndex) { return select(a, p, pivotIndex - 1, k); }
        else if (k == pivotIndex) { return a[pivotIndex - 1]; }
        else { return select(a, pivotIndex + 1, r, k); }
    }

    /*
    // The meaning of k is related to the PARTIAL array and mark the difference as "HERE"
    private static int select(int[] a, int p, int r, int k) {
        if ((r - p + 1) <= 5) {
            a = insertionSort(a, p, r);

            return a[p - 1 + k - 1]; // HERE
        }

        int groups = (r - p + 1) / 5;
        int[] medians;
        if((r - p + 1) % 5 == 0) { medians = new int[groups]; } // only complete groups
        else { medians = new int[groups + 1]; } // a incomplete group

        for(int i = 0; i < groups; i++) { // for complete groups
            medians[i] = getMedian(a, p + 5 * i, p + 5 * i + 4);
        }
        if(p + 5 * groups <= r) { // only for a incomplete group
            medians[groups] = getMedian(a, p + 5 * groups, r);
        }

        int pivot = select(medians, 1, medians.length, (medians.length + 1) / 2);
        int pivotIndex = medianPartition(a, p, r, pivot);

        int another = pivotIndex - p + 1; // HERE
        if (k < another) { return select(a, p, pivotIndex - 1, k); } // HERE
        else if (k == another) { return a[pivotIndex - 1]; } // HERE
        else { return select(a, pivotIndex + 1, r, k - another); } // HERE
    }
    */
}