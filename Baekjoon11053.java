import java.util.Scanner;

public class Baekjoon11053 {
    private static int N;
    private static int[] A;
    private static int[] LENGTH;
    private static int MAX = 1;

    public static void main(String args[]) {
        initiateA(); // A 초기화

        // LENGTH 연산
        LENGTH[0] = 1;
        for (int i = 1; i < N; i++) {
            LENGTH[i] = findMax(i) + 1;
        }

        for (int i = 0; i < N; i++) {
            if (LENGTH[i] > MAX) {
                MAX = LENGTH[i];
            }
        }

        System.out.println(MAX);
    }

    private static void initiateA() {
        Scanner scanner = new Scanner(System.in);

        N = scanner.nextInt();
        A = new int[N];
        LENGTH = new int[N];

        for (int i = 0; i < N; i++) {
            A[i] = scanner.nextInt();
        }

        scanner.close();
    }

    private static int findMax(int index) {
        int maxValue = 0; // 찾지 못하면 0 반환

        for (int i = 0; i < index; i++) {
            if (A[index] > A[i] && LENGTH[i] > maxValue) {
                maxValue = LENGTH[i];
            }
        }

        return maxValue;
    }
}

/*
     A: 1   2   10  7   4   5   8   9
LENGTH: 1   2   3   3   3   4   5   6
   해설: A의 i번째 값보다 작은 수의 LENGTH 중 가장 큰 값 + 1 = LENGTH의 i번째 값
 */
