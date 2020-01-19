import java.util.Scanner;
        import java.util.Stack;

public class Baekjoon7576 {
    private static int[][] matrix = null;
    private static Stack<String> before = new Stack<>();
    private static Stack<String> after = new Stack<>();
    private static int M = 0;
    private static int N = 0;
    private static int unripe = 0;
    private static int count = 0;

    public static void main(String args[]) {
        boolean ready = initiate();
        if (!ready) {
            System.out.println(0);

            return;  // 종료 조건 1: 저장될 때부터 모든 토마토가 익어 있는 상태
        }

        boolean result = false;
        do {
            result = check(result);

            if (result) {
                count++;

                before = (Stack<String>) after.clone();
                after.clear();
            } else {
                if (unripe > 0) {
                    System.out.println(-1);

                    return; // 종료 조건 2: 토마토가 모두 익지 못하는 상황
                }
                System.out.println(count);

                return; // 종료 조건 3: 정상
            }

            result = !result;
        } while (!result);
    }

    private static boolean initiate() {
        Scanner scanner = new Scanner(System.in);

        M = scanner.nextInt();
        N = scanner.nextInt();
        matrix = new int[M][N];

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                matrix[x][y] = scanner.nextInt();

                if (matrix[x][y] == 1) {
                    before.push(x + "," + y);
                } else if (matrix[x][y] == 0) {
                    unripe++;
                }
            }
        }

        if (unripe == 0) {
            return false;
        }

        return true;
    }

    private static boolean check(boolean change) {
        while (before.size() != 0) {
            String[] coordinate = before.pop().split(",");
            int x = Integer.valueOf(coordinate[0]);
            int y = Integer.valueOf(coordinate[1]);

            if (x >= 1 && matrix[x - 1][y] == 0) {
                matrix[x - 1][y] = 1;
                after.push((x - 1) + "," + y);

                unripe--;
                change = true;
            }
            if (y >= 1 && matrix[x][y - 1] == 0) {
                matrix[x][y - 1] = 1;
                after.push(x + "," + (y - 1));

                unripe--;
                change = true;
            }
            if (x < M - 1 && matrix[x + 1][y] == 0) {
                matrix[x + 1][y] = 1;
                after.push((x + 1) + "," + y);

                unripe--;
                change = true;
            }
            if (y < N - 1 && matrix[x][y + 1] == 0) {
                matrix[x][y + 1] = 1;
                after.push(x + "," + (y + 1));

                unripe--;
                change = true;
            }
        }

        return change;
    }
}
