package com.avl;


public class AvlTree<T extends Comparable<T>> {

    private Node<T> root;
    private int size;

    public AvlTree(T... keys) {
        if (keys == null) {
        	return;
        }
        insert(keys);
    }

    /**
     * Computes the number of child nodes in the left subtree of {@code node}.
     * Runs in constant time.
     * 
     * @param  node the node whose left subtree size to compute.
     * @return the number of child nodes in the left subtree.
     */
    private int getLeftSubtreeSize(Node<T> node) {
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
    private Node<T> getNode(Node<T> root, int index) {
        Node<T> current = root;

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
            T b = getNode(root, size / 2 - 1).key;
            T a = getNode(root, size / 2).key;
            return 0.5 * (a + b);
        } else {
            return getNode(root, size / 2).key;
        }
    }

    private Node<T> insert(Node<T> parent, T key) {
        if (parent == null) {
            ++size;
            return new Node<T>(key);
        }
        
        if (key.compareTo(parent.key) < 0) {
            parent.left = insert(parent.left, key);
        } else {
            parent.right = insert(parent.right, key);
        }

        return balance(parent);
    }

    private Node<T> balance(Node<T> p) {
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

    private Node<T> rotateRight(Node<T> p) {
        Node<T> q = p.left;
        p.left = q.right;
        q.right = p;
        fixHeightAndChildCount(p);
        fixHeightAndChildCount(q);
        return q;
    }

    private Node<T> rotateLeft(Node<T> q) {
        Node<T> p = q.right;
        q.right = p.left;
        p.left = q;
        fixHeightAndChildCount(q);
        fixHeightAndChildCount(p);
        return p;
    }

    private int height(Node<T> p) {
        return p == null ? 0 : p.height;
    }

    private int bfactor(Node<T> p) {
        return height(p.right) - height(p.left);
    }

    private void fixHeightAndChildCount(Node<T> p) {
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

    public void insert(T... keys) {
        for (T key : keys) {
            root = insert(root, key);
        }
    }

    private static final class Node<T> {

        private Node<T> left;
        private Node<T> right;
        private final T key;
        private int height;
        private int childCount;

        private Node(T value) {
            key = value;
            height = 1;
        }

        @Override
        public String toString() {
            return key == null ? "" : key.toString();
        }
    }
}