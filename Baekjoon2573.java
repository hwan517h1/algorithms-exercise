import java.util.Scanner;
import java.util.Stack;

public class Baekjoon2573 {
    // 빙하 상태
    private static final int UNITED = 1;
    private static final int DIVIDED = 0;
    private static final int GONE = -1;

    private static int m, n;
    private static int[][] matrix;
    private static boolean[][] visited;
    private static Stack<Element> before;
    private static Stack<Element> after;
    private static int count = 0; // 방문 횟수
    private static int year = 1;

    public static void main(String[] args) {
        initiate();

        boolean next = false;
        do {
            int result = pass();

            if (result == UNITED) {
                next = true;

                year++;
            } else if (result == DIVIDED) {
                next = false;

                System.out.println(year);
            } else if (result == GONE) {
                next = false;

                System.out.println(0);
            }

        } while(next);
    }

    private static void initiate() {
        Scanner scanner = new Scanner(System.in);

        m = scanner.nextInt();
        n = scanner.nextInt();
        matrix = new int[m][n];
        visited = new boolean[m][n];

        before = new Stack<>();
        after = new Stack<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int value = scanner.nextInt();

                matrix[i][j] = value;

                if (value > 0) {
                    before.push(new Element(i, j, value));
                }
            }
        }

        scanner.close();
    }

    private static int pass() {
        while (!before.isEmpty()) {
            Element element = before.pop();

            int remain = element.getValue() - melt(element.getX(), element.getY());
            if (remain > 0) {
                element.setValue(remain);
            } else {
                element.setValue(0);
            }

            after.push(element);
        }

        while (!after.isEmpty()) {
            Element element = after.pop();

            matrix[element.getX()][element.getY()] = element.getValue();

            if (element.getValue() > 0) {
                before.push(element);
            }
        }

        if (!before.isEmpty()) {
            Element element = before.peek();

            if (divided(element.getX(), element.getY())) {
                return DIVIDED;
            }

            // 초기화
            count = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    visited[i][j] = false;
                }
            }
        } else {
            return GONE;
        }

        return UNITED;
    }

    private static int melt(int x, int y) {
        int minus = 0;

        if (x > 0 && matrix[x - 1][y] == 0) { minus++; }
        if (x < m - 1 && matrix[x + 1][y] == 0) { minus++; }
        if (y > 0 && matrix[x][y - 1] == 0) { minus++; }
        if (y < n - 1 && matrix[x][y + 1] == 0) { minus++; }

        return minus;
    }

    private static boolean divided(int x, int y) {
        visit(x, y);

        if (count != before.size()) {
            return true;
        }

        return false;
    }

    private static void visit(int x, int y) {
        visited[x][y] = true;
        count++;

        if (x > 0 && matrix[x - 1][y] > 0 && !visited[x - 1][y]) { visit(x - 1, y); }
        if (x < m - 1 && matrix[x + 1][y] > 0 && !visited[x + 1][y]) { visit(x + 1, y); }
        if (y > 0 && matrix[x][y - 1] > 0 && !visited[x][y - 1]) { visit(x, y - 1); }
        if (y < n - 1 && matrix[x][y + 1] >  0&& !visited[x][y + 1]) { visit(x, y + 1); }
    }
}

class Element {
    private int x; // x-coordinate
    private int y; // y-coordinate
    private int value;

    Element(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
