import java.util.LinkedList;


public class RadixSort {

    public static void main(String[] args) {
        int[] input = {5, 4, 3, 2, 10, 0, 9, 8, 7, -21, -111, 6, 31, 11, 23, 100, 103, 201, 28, 43, 37, 1, -1};

        // int 범위: -2,147,483,648 ~ 2,147,483,647
        process(input, 10);

        for (int i : input) {
            System.out.print(i + " ");
        }
    }

    private static LinkedList<Integer> positive = new LinkedList<>();
    private static LinkedList<Integer> negative = new LinkedList<>();

    public static void process(int[] array, int digits) {
        for (int i : array) {
            if (i >= 0) { positive.add(i); }
            else { negative.add(i); }
        }

        // 0 미만 정렬
        int countNegative = negative.size();
        int[] tmp1 = new int[countNegative];
        for (int i = 0;  i < countNegative; i++) {
            tmp1[i] = negative.get(i) * -1;
        }
        sort(tmp1, digits);
        for (int i = 0;  i < countNegative; i++) {
            array[i] = tmp1[countNegative - 1 - i] * -1;
        }

        // 0 이상 정렬
        int countPositive = positive.size();
        int[] tmp2 = new int[countPositive];
        for (int i = 0;  i < countPositive; i++) {
            tmp2[i] = positive.get(i);
        }
        sort(tmp2, digits);
        for (int i = 0;  i < countPositive; i++) {
            array[countNegative + i] = tmp2[i];
        }
    }

    @SuppressWarnings("unchecked")
    private static LinkedList<Integer>[] buckets = new LinkedList[] {
            new LinkedList<Integer>(), new LinkedList<Integer>(),
            new LinkedList<Integer>(), new LinkedList<Integer>(),
            new LinkedList<Integer>(), new LinkedList<Integer>(),
            new LinkedList<Integer>(), new LinkedList<Integer>(),
            new LinkedList<Integer>(), new LinkedList<Integer>()
    };

    // digits: 가장 큰 자릿수
    public static void sort(int[] array, int digits) {
        int modulo = 10;
        int division = 1;

        for (int i = 0; i < digits; i++, modulo *= 10, division *= 10) {
            for (int j = 0; j < array.length; j++) {
                int bucket = (array[j] % modulo) / division;

                buckets[bucket].add(array[j]);
            }

            int position = 0;

            for (int j = 0; j < buckets.length; j++) {
                Integer value;

                while ((value = buckets[j].poll()) != null) {
                    array[position++] = value;
                }
            }
        }
    }
}


