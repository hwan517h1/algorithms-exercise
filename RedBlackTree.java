class Node {
    // Basic red-black tree
    public int key;
    public int color;
    public Node parent;
    public Node left;
    public Node right;

    // using for select(), rank() in Order statistics tree
    public int size;

    public Node(int key, int color, int size, Node parent, Node left, Node right) {
        this.key = key;
        this.color = color;
        this.size = size;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }
}

class Tree {
    public Node root;
    public Node nil;

    public static final int BLACK = 1;
    public static final int RED = 2;

    public Tree(Node root, Node nil) {
        this.root = root;
        this.nil = nil;
    }

    public Node find(Tree T, int i) {
        Node x = T.root;

        while (x != T.nil) {
            if(i == x.key) { return x; } // 해당 key를 가진 node가 존재함
            else if(i < x.key) { x = x.left; }
            else { x = x.right; }
        }

        return null; // 해당 key를 가진 node가 존재하지 않음
    }

    public void rotateLeft(Tree T, Node x) { // a == x.left, y == x.right, b == y.left, c == y.right 경우
        Node y = x.right;

        x.right = y.left; // x.right = b(부모 입장)
        if (y.left != T.nil) { y.left.parent = x; } // x.right = b(자식 입장)

        y.parent = x.parent; // x의 부모와의 관계 정리
        if (x.parent == T.nil) { T.root = y; }
        else if (x == x.parent.left) { x.parent.left = y; }
        else { x.parent.right = y; }

        y.left = x; // x와 y의 관계 정리 1
        x.parent = y; // x와 y의 관계 정리 2

        y.size = x.size; // x와 y의 size 정리 1
        x.size = x.left.size + x.right.size + 1; // x와 y의 size 정리 2
    }

    public void rotateRight(Tree T, Node y) { // a == x.left, b == x.right, x == y.left, c == y.right 경우
        Node x = y.left;

        y.left = x.right; // y.left = b(부모 입장)
        if (x.right != T.nil) { x.right.parent = y; } // y.left = b(자식 입장)

        x.parent = y.parent; // y의 부모와의 관계 정리
        if (y.parent == T.nil) { T.root = x; }
        else if (y == y.parent.right) { y.parent.right = x; }
        else { y.parent.left = x; }

        x.right = y; // x와 y의 관계 정리 1
        y.parent = x; // x와 y의 관계 정리 2

        x.size = y.size; // x와 y의 size 정리 1
        y.size = y.left.size + y.right.size + 1; // x와 y의 size 정리 2
    }

    public int insert(Tree T, Node n) {
        Node x = T.root;
        Node p = T.nil;
        while (x != T.nil) { // key 따라 n 삽입 위치 탐색
            p = x;
            x.size++; // size 증가
            if (n.key < x.key) { x = x.left; }
            else { x = x.right; }
        }

        n.parent = p; // n 삽입 위치
        if (p == T.nil) { // Empty tree
            T.root = n;
            n.parent = nil;
            n.color = BLACK;

            return n.key;
        }
        else if (n.key < p.key) { p.left = n; }
        else { p.right = n; }

        fixUpInsertion(T, n); // Tree 특성 유지 목적

        n.size = n.left.size + n.right.size + 1;

        return n.key;
    }

    public void fixUpInsertion(Tree T, Node x) {
        while (x.parent.color == RED) { // RED node 연속 오류 해결
            if (x.parent == x.parent.parent.left) {
                Node u = x.parent.parent.right; // x의 삼촌
                if (u.color == RED) {
                    x.parent.color = BLACK;
                    u.color = BLACK;
                    x.parent.parent.color = RED;

                    x = x.parent.parent;
                } else {
                    if (x == x.parent.right) {
                        x = x.parent;

                        rotateLeft(T, x);
                    }

                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;

                    rotateRight(T, x.parent.parent);
                }
            } else {
                Node u = x.parent.parent.left;
                if (u.color == RED) {
                    x.parent.color = BLACK;
                    u.color = BLACK;
                    x.parent.parent.color = RED;

                    x = x.parent.parent;
                } else {
                    if (x == x.parent.left) {
                        x = x.parent;

                        rotateRight(T, x);
                    }

                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;

                    rotateLeft(T, x.parent.parent);
                }
            }
        }

        T.root.color = BLACK;
    }

    public void transplant(Tree T, Node d, Node t) { // Subtree 교체(d 삭제 후 t 이식)
        if (d.parent == T.nil) { T.root = t; } // 부모 입장
        else if (d == d.parent.left) { d.parent.left = t; }
        else { d.parent.right = t; }

        t.parent = d.parent; // 자식 입장
    }

    public Node findMinimum(Tree T, Node n) { // Swap with the minimum of right subtree
        Node x = n;

        while (x.left != T.nil) { x = x.left; }

        return x;
    }

    public int delete(Tree T, Node z) {
        Node x;
        Node y = z;
        int yOriginalColor = y.color;

        if (z.left == T.nil) {
            x = z.right;
            transplant(T, z, z.right);
        } else if (z.right == T.nil) {
            x = z.left;
            transplant(T, z, z.left);
        } else {
            y = findMinimum(T, z.right);
            yOriginalColor = y.color;
            x = y.right;

            if (y.parent == z) { x.parent = y; }
            else {
                transplant(T, y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            transplant(T, z, y);
            y.left= z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        y.size = z.size;

        Node tmp = x;
        while (tmp != T.root) {
            tmp = tmp.parent;
            tmp.size--;
        }

        if (yOriginalColor == BLACK) { fixUpDeletion(T, x); }

        return z.key;
    }

    public void fixUpDeletion(Tree T, Node x) {
        while ((x != T.root) && (x.color == BLACK)) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateLeft(T, x.parent);
                    w = x.parent.right;
                }
                if ((w.left.color == BLACK) && (w.right.color == BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.right.color == BLACK) {
                        w.left.color = BLACK;
                        w.color = RED;
                        rotateRight(T, w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    rotateLeft(T, x.parent);
                    x = T.root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == RED) {
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateRight(T, x.parent);
                    w = x.parent.left;
                }
                if ((w.right.color == BLACK) && (w.left.color == BLACK)) {
                    w.color = RED;
                    x = x.parent;
                } else {
                    if (w.left.color == BLACK) {
                        w.right.color = BLACK;
                        w.color = RED;
                        rotateLeft(T, w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rotateRight(T, x.parent);
                    x = T.root;
                }
            }
        }
        x.color = BLACK;
    }

    public Node select(Node x, int i) {
        int rank = x.left.size + 1;

        if (i == rank) { return x; }
        else if (i < rank) { return select(x.left, i); }
        else { return select(x.right, i - rank); }
    }

    public int rank(Tree T, Node n) {
        int rank = n.left.size + 1;
        Node p = n;

        while (p != T.root) {
            if (p == p.parent.right) { rank = rank + p.parent.left.size + 1; }
            p = p.parent;
        }

        return rank;
    }

    public void print(Tree T, Node x) { // In-order traversal(중위 순회)
        if (x == nil) {
            System.out.println("(Empty)");

            return;
        }

        if (x.left != T.nil) { print(T, x.left); }

        System.out.print("(Key: " + x.key + ", Parent: " + x.parent.key + ", Size: " + x.size);
        if (x.color == 1) { System.out.println(", Color: BLACK)"); }
        else { System.out.println(", Color: RED)"); }

        if (x.right != T.nil) { print(T, x.right);}
    }
}


public class RedBlackTree {
    public static Node nil;
    public static Tree tree;

    public static final int BLACK = 1;
    public static final int RED = 2;

    public static void main(String[] args) {
        nil = new Node(0, 1, 0, nil, nil, nil);
        tree = new Tree(nil, nil); // Empty tree

        init();
        delete(1);
        select(1);
        rank(1);
        insert(26);
        insert(17);
        insert(41);
        insert(43);
        tree.print(tree, tree.root);
        System.out.println();
        delete(26);
        delete(41);
        tree.print(tree, tree.root);
        System.out.println();
        select(2);
        select(3);
        rank(100);
        rank(17);
    }

    public static void init() {
        nil = new Node(0, 1, 0, nil, nil, nil);

        tree = new Tree(nil, nil); // Empty tree
    }

    public static int insert(int x) {
        if (x < 1) {
            return 0;
        } else if (tree.find(tree, x) == null) {
            Node n = new Node(x, 2, 1, nil, nil, nil);

            return tree.insert(tree, n);
        } else {
            return 0;
        }
    }

    public static int delete(int x) {
        Node n = tree.find(tree, x);

        if (n == null) {
            return 0;
        } else {
            return tree.delete(tree, n);
        }
    }

    public static void select(int i) {
        if (i - 1 < tree.root.size) {
            System.out.println(tree.select(tree.root, i).key);

            return;
        } else {
            System.out.println("Error in select");

            return;
        }
    }

    public static void rank(int x) {
        Node n = tree.find(tree, x);

        if (n == null) {
            System.out.println("Error in rank");

            return;
        } else {
            System.out.println(tree.rank(tree, n));

            return;
        }
    }
}
