package com.avl;

public class AvlTree {

    private Node root;
    private int size;

    public AvlTree(int... keys) {
        if (keys == null) {
        	return;
        }
        insert(keys);
    }

    /**
     * Computes the amount of child nodes in the left subtree of {@code node}.
     * Runs in constant time.
     * 
     * @param  node the node whose left subtree size to compute.
     * @return the amount of child nodes in the left subtree.
     */
    private int getLeftSubtreeSize(Node node) {
        int tmp = node.childCount;

        if (node.right != null) {
            tmp -= (node.right.childCount + 1);
        }

        return tmp;
    }

    /**
     * Returns the value of {@code index}th (in order) entry. Runs in 
     * logarithmic time.
     * 
     * @param root  the root of the tree.
     * @param index the index of the entry whose value to get.
     * @return the value of {@code index}th value.
     */
    private Node getNode(Node root, int index) {
        Node current = root;

        for (;;) {
            int leftSubtreeSize = getLeftSubtreeSize(current);

            if (index == leftSubtreeSize) {
                return current;
            }

            if (index > leftSubtreeSize) {
                index -= (leftSubtreeSize + 1);
                current = current.right;
            } else {
                current = current.left;
            }
        }
    }

    /**
     * Computes the median of this tree. Makes at most two calls to logarithmic 
     * time methods.
     * 
     * @return the median of this tree.
     */
    public double getMedian() {
        if (size == 0) {
            throw new IllegalStateException(
                    "Asking for median from an empty tree.");
        }

        if (size % 2 == 0) {
            int b = getNode(root, size / 2 - 1).key;
            int a = getNode(root, size / 2).key;
            return 0.5 * (a + b);
        } else {
            return getNode(root, size / 2).key;
        }
    }

    private Node insert(Node parent, int key) {
        if (parent == null) {
            ++size;
            return new Node(key);
        }

        if (key < parent.key) {
            parent.left = insert(parent.left, key);
        } else {
            parent.right = insert(parent.right, key);
        }

        return balance(parent);
    }

    private Node balance(Node p) {
        fixHeightAndChildCount(p);
        if (bfactor(p) == 2) {
            if (bfactor(p.right) < 0) {
                p.right = rotateRight(p.right);
            }
            return rotateLeft(p);
        }
        if (bfactor(p) == -2) {
            if (bfactor(p.left) > 0) {
                p.left = rotateLeft(p.left);
            }
            return rotateRight(p);
        }
        return p;
    }

    private Node rotateRight(Node p) {
        Node q = p.left;
        p.left = q.right;
        q.right = p;
        fixHeightAndChildCount(p);
        fixHeightAndChildCount(q);
        return q;
    }

    private Node rotateLeft(Node q) {
        Node p = q.right;
        q.right = p.left;
        p.left = q;
        fixHeightAndChildCount(q);
        fixHeightAndChildCount(p);
        return p;
    }

    private int height(Node p) {
        return p == null ? 0 : p.height;
    }

    private int bfactor(Node p) {
        return height(p.right) - height(p.left);
    }

    private void fixHeightAndChildCount(Node p) {
        int hl = height(p.left);
        int hr = height(p.right);
        p.height = (hl > hr ? hl : hr) + 1;
        p.childCount = 0;
        if (p.left != null) {
            p.childCount = p.left.childCount + 1;
        }
        if (p.right != null) {
            p.childCount += p.right.childCount + 1;
        }
    }

    public void insert(int... keys) {
        for (int key : keys) {
            root = insert(root, key);
        }
    }

    private static final class Node {

        private Node left;
        private Node right;
        private final int key;
        private int height;
        private int childCount;

        private Node(int value) {
            key = value;
            height = 1;
        }

        @Override
        public String toString() {
            return Integer.toString(key);
        }
    }
}