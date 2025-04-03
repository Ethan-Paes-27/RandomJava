package DSA;

public class BinarySearchTree {

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        bst.insert(5);
        bst.insert(4);
        bst.insert(10);
        bst.insert(20);
        bst.inOrder();
        System.out.println(bst.search(5));
        System.out.println(bst.search(7));
        System.out.println(bst.findMax());
        System.out.println(bst.findMin());
        bst.delete(4);
        bst.inOrder();
        bst.delete(5);
        bst.inOrder();
    }

    TreeNode root;

    public BinarySearchTree() {
        root = null;
    }

    public BinarySearchTree(int n) {
        root = new TreeNode(n);
    }

    public BinarySearchTree(TreeNode n) {
        root = n;
    }

    public void insert(int data) {
        root = insertRec(root, data);
    }

    private TreeNode insertRec(TreeNode root, int data) {
        if (root == null) {
            root = new TreeNode(data);
            return root;
        }
        if (root.data == data) {
            return root;
        } else if (data < root.data) {
            root.left = insertRec(root.left, data);
            return root;
        } else {
            root.right = insertRec(root.right, data);
            return root;
        }
    }

    public boolean search(int data) {
        return searchRec(root, data);
    }

    private boolean searchRec(TreeNode root, int data) {
        if (root == null) {
            return false;
        } else if (root.data == data) {
            return true;
        } else
            return searchRec(root.left, data) || searchRec(root.right, data);
    }

    public int findMin() {
        TreeNode dupe = root;

        while (dupe.left != null) {
            dupe = dupe.left;
        }

        return dupe.data;
    }

    public int findMax() {
        TreeNode dupe = root;

        while (dupe.right != null) {
            dupe = dupe.right;
        }

        return dupe.data;
    }

    public void delete(int data) {
        root = deleteRec(root, data);
    }

    private TreeNode deleteRec(TreeNode root, int data) {
        if (root == null) {
            return null;
        }

        if (root.data == data) {
            if (root.left == null && root.right == null) {
                root = null;
                return root;
            } else if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            } else {

                TreeNode parent = root;
                TreeNode minNode = root.right;

                while (minNode.left != null) {
                    parent = minNode; 
                    minNode = minNode.left;
                }

                root.data = minNode.data;

                if (parent.left == minNode) {
                    parent.left = minNode.right; 
                } else {
                    parent.right = minNode.right;
                }
            }
        }
        else if (data < root.data) {
            root.left = deleteRec(root.left, data);
        }
        else root.right = deleteRec(root.right, data);

        return root;
    }

    private TreeNode findMinNode(TreeNode root) {
        TreeNode dupe = root;

        while (dupe.left != null) {
            dupe = dupe.left;
        }

        return dupe;
    }

    public void inOrder() {
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(TreeNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.print(root.data + " ");
            inOrderRec(root.right);
        }

    }

    // public void preOrder() {

    // }

    // private void preOrderRec(TreeNode Root) {

    // }

    // public void postOrder() {

    // }

    // private void postOrderRec(TreeNode root) {

    // }
}

class TreeNode {
    int data;
    TreeNode left, right;

    public TreeNode(int data) {
        this.data = data;
        this.left = this.right = null;
    }
}
