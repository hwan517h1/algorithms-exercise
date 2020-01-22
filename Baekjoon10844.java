import java.util.Scanner;

public class Baekjoon10844 {
    private static int N = 0;
    private static long[] BEFORE = new long[10];
    private static long[] AFTER = new long[10];

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        scanner.close();

        // 초기화
        BEFORE[0] = 0;
        for (int i = 1; i < 10; i++) {
            BEFORE[i] = 1;
        }

        for (int i = 1; i < N; i++) { // 연산 수
            for (int j = 0; j < 10; j++) {
                if (j == 0) {
                    AFTER[1] += BEFORE[0];
                } else if (j == 9) {
                    AFTER[8] += BEFORE[9];
                } else {
                    AFTER[j - 1] += BEFORE[j];
                    AFTER[j + 1] += BEFORE[j];
                }
            }

            for (int j = 0; j < 10; j++) {
                AFTER[j] = AFTER[j] % 1_000_000_000; // 오버플로우 방지
            }

            BEFORE = AFTER.clone();
            AFTER = new long[10];
        }

        int result = 0;
        for (int i= 0; i < 10; i++) {
            result += BEFORE[i];

            result = result % 1_000_000_000;  // 오버플로우 방지
        }
        System.out.println(result);
    }
}
