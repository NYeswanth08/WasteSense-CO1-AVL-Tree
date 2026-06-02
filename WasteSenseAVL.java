class Node {
    int binId;
    int height;
    Node left, right;

    Node(int binId) {
        this.binId = binId;
        height = 1;
    }
}

public class WasteSenseAVL {

    Node root;

    int height(Node n) {
        if (n == null)
            return 0;
        return n.height;
    }

    int getBalance(Node n) {
        if (n == null)
            return 0;
        return height(n.left) - height(n.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, int binId) {

        if (node == null)
            return new Node(binId);

        if (binId < node.binId)
            node.left = insert(node.left, binId);
        else if (binId > node.binId)
            node.right = insert(node.right, binId);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && binId < node.left.binId)
            return rightRotate(node);

        if (balance < -1 && binId > node.right.binId)
            return leftRotate(node);

        if (balance > 1 && binId > node.left.binId) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && binId < node.right.binId) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    boolean search(Node node, int key) {

        if (node == null)
            return false;

        if (node.binId == key)
            return true;

        if (key < node.binId)
            return search(node.left, key);

        return search(node.right, key);
    }

    void inorder(Node node) {

        if (node != null) {
            inorder(node.left);
            System.out.print(node.binId + " ");
            inorder(node.right);
        }
    }

    public static void main(String[] args) {

        WasteSenseAVL tree = new WasteSenseAVL();

        int bins[] = {110, 101, 120, 105, 115};

        System.out.println("===== WasteSense Waste Bin Management =====\n");

        System.out.println("Waste Bin IDs Inserted:");

        for (int bin : bins) {
            tree.root = tree.insert(tree.root, bin);
            System.out.print(bin + " ");
        }

        System.out.println("\n");

        int searchBin = 110;

        System.out.println("Searching Waste Bin ID " + searchBin);

        if (tree.search(tree.root, searchBin))
            System.out.println("Result: Bin Found");
        else
            System.out.println("Result: Bin Not Found");

        System.out.println("\nSorted Waste Bin Management Report:");

        tree.inorder(tree.root);

        System.out.println("\n\nAVL Tree Balanced Successfully");
        System.out.println("Time Complexity:");
        System.out.println("Insert = O(log n)");
        System.out.println("Search = O(log n)");
        System.out.println("Delete = O(log n)");
    }
}