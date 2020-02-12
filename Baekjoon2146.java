import java.util.HashMap;
import java.util.Scanner;

public class Baekjoon2146 {
    private static int N, min;
    private static int[][] input;
    private static boolean[][] visited;

    public static void main(String[] args) {
        initiate();

        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (isCoast(i, j)) {
                    hashMap.put(i + "," + j, input[i][j]);
                }
            }
        }

        int[][] coast1 = new int[hashMap.size()][3];
        int[][] coast2 = new int[hashMap.size()][3];

        int index = 0;
        for (String key : hashMap.keySet()) {
            int i = Integer.valueOf(key.split(",")[0]);
            int j = Integer.valueOf(key.split(",")[1]);

            coast1[index][0] = i;
            coast1[index][1] = j;
            coast1[index][2] = input[i][j];

            coast2[index][0] = i;
            coast2[index][1] = j;
            coast2[index][2] = input[i][j];

            index++;
        }

        for (int i = 0; i < hashMap.size(); i++) {
            for (int j = 0; j < hashMap.size(); j++) {
                if (coast1[i][2] != coast2[j][2]) {
                    int distance = Math.abs(coast1[i][0] - coast2[j][0]) + Math.abs(coast1[i][1] - coast2[j][1]);
                    if (coast1[i][0] != coast2[j][0] || coast1[i][1] != coast2[j][1]) {
                        distance--;
                    }

                    if (min > distance) {
                        min = distance;
                    }
                }
            }
        }

        System.out.println(min);
    }

    private static void initiate() {
        Scanner scanner = new Scanner(System.in);

        N = scanner.nextInt();
        input = new int[N][N];
        visited = new boolean[N][N];
        min = N * N;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (scanner.nextInt() > 0) {
                    input[i][j] = 1; // 육지는 양수이다.
                }
            }
        }

        int label = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (input[i][j] > 0 && !visited[i][j]) {
                    visit(i, j, label); // 섬 구분하기

                    label++;
                }
            }
        }

//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                System.out.print(input[i][j] + " ");
//            }
//
//            System.out.println();
//        }
    }

    private static void visit(int x, int y, int label) {
        visited[x][y] = true;
        input[x][y] = label;

        if (x > 0 && input[x - 1][y] > 0 && !visited[x - 1][y]) {
            input[x - 1][y] = label;

            visit(x - 1, y, label);
        }
        if (x < N - 1 && input[x + 1][y] > 0 && !visited[x + 1][y]) {
            input[x + 1][y] = label;

            visit(x + 1, y, label);
        }
        if (y > 0 && input[x][y - 1] > 0 && !visited[x][y - 1]) {
            input[x][y - 1] = label;

            visit(x, y - 1, label);
        }
        if (y < N - 1 && input[x][y + 1] > 0&& !visited[x][y + 1]) {
            input[x][y + 1] = label;

            visit(x, y + 1, label);
        }
    }

    private static boolean isCoast(int x, int y) {
        boolean result = false;

        if (input[x][y] > 0) {
            if (x > 0 && input[x - 1][y] == 0) {
                result = true;
            } else if (x < N - 1 && input[x + 1][y] == 0) {
                result = true;
            } else if (y > 0 && input[x][y - 1] == 0) {
                result = true;
            } else if (y < N - 1 && input[x][y + 1] == 0) {
                result = true;
            }
        }

        return result;
    }
}




