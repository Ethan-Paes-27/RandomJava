package DSA;

public class BinarySearchTree {

    public static void main(String[] args) {
        // Test 1: Inserting Duplicate Values
        System.out.println("Test 1: Inserting Duplicate Values");
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(5);
        bst.insert(5); // duplicate
        bst.insert(10);
        bst.insert(10); // duplicate
        bst.insert(10); // duplicate
        System.out.println("Inorder Traversal (after inserting duplicates):");
        bst.inOrder(); // Expected: 5 → 5 → 10 → 10 → 10 (or no duplicates if ignored)
        System.out.println();

        // Test 2: Deleting a Node That Doesn't Exist
        System.out.println("Test 2: Deleting Node that Doesn't Exist");
        System.out.println("Deleting 99 (not in tree)...");
        bst.delete(99); // Expected: "99 not found in the tree."
        bst.inOrder(); // Should remain unchanged
        System.out.println();

        // Test 3: Deleting the Last Node (Empty Tree)
        System.out.println("Test 3: Deleting Last Node (Empty Tree)");
        BinarySearchTree bst2 = new BinarySearchTree();
        bst2.insert(42);
        System.out.println("Deleting the only node...");
        bst2.delete(42); // Expected: Tree becomes empty
        System.out.println("Is the tree empty? " + bst2.isEmpty()); // Expected: true
        System.out.println();

        // Test 4: Traversals on an Empty Tree
        System.out.println("Test 4: Traversals on an Empty Tree");
        BinarySearchTree bst3 = new BinarySearchTree();
        System.out.println("Inorder traversal on empty tree:");
        bst3.inOrder(); // Expected: "Tree is empty."
        System.out.println("Preorder traversal on empty tree:");
        bst3.preOrder(); // Expected: "Tree is empty."
        System.out.println("Postorder traversal on empty tree:");
        bst3.postOrder(); // Expected: "Tree is empty."
        System.out.println();
    }

    private TreeNode root;

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

    public boolean isEmpty() {
        return root == null;
    }
    
    public TreeNode find(int data) {
        return findRec(root, data);
    }

    private TreeNode findRec(TreeNode root, int data) {
        if (root == null) {
            return null;
        } else if (root.data == data) {
            return root;
        } else if (data < root.data) {
            return findRec(root.left, data);
        } else
            return findRec(root.right, data);
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
        if (find(data) == null) {
            System.out.println(data + " not found in the tree.");
        } else {
            root = deleteRec(root, data);
        }
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
        } else if (data < root.data) {
            root.left = deleteRec(root.left, data);
        } else
            root.right = deleteRec(root.right, data);

        return root;
    }

    public void inOrder() {
        if (root == null) {
            System.out.println("Tree is empty.");
        } else {
            inOrderRec(root);
            System.out.print("null");
            System.out.println();
        }
    }

    private void inOrderRec(TreeNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.print(root.data + " -> ");
            inOrderRec(root.right);
        }
    }

    public void preOrder() {
        if (root == null) {
            System.out.println("Tree is empty.");
        } else {
            preOrderRec(root);
            System.out.print("null");
            System.out.println();
        }
    }

    private void preOrderRec(TreeNode root) {
        if (root != null) {
            System.out.print(root.data + " -> ");

            if (root.left != null) {
                preOrderRec(root.left);
            }
            if (root.right != null) {
                preOrderRec(root.right);
            }
        }
    }

    public void postOrder() {
        if (root == null) {
            System.out.println("Tree is empty.");
        } else {
            postOrderRec(root, root.data);
            System.out.println();
        }
    }

    private void postOrderRec(TreeNode root, int headData) {
        if (root != null) {
            if (root.left != null) {
                postOrderRec(root.left, headData);
            }
            if (root.right != null) {
                postOrderRec(root.right, headData);
            }
            int data = root.data;
            if (data == headData) {
                System.out.print(data);
            } else {
                System.out.print(data + " -> ");
            }
        }
    }
}

class TreeNode {
    int data;
    TreeNode left, right;

    public TreeNode(int data) {
        this.data = data;
        this.left = this.right = null;
    }
}
