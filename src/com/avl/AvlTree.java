package com.avl;


public class AvlTree {

	private Node root;
	private int size;
	private int oldSize;
	private int leftSubtreeSize;
	private int rightSubtreeSize;

	public AvlTree(int... keys) {
		if (keys == null) {
			return;
		}
		insert(keys);
	}

	private Node insert(Node parent, int key) {
		if (parent == null) {
			size++;
			return new Node(key);
		}
		if (key < parent.key) {
			parent.left = insert(parent.left, key);
			if (size > oldSize) {
				leftSubtreeSize++;
				oldSize = size;
			}
		} else if (key > parent.key) {
			parent.right = insert(parent.right, key);
			if (size > oldSize) {
				rightSubtreeSize++;
				oldSize = size;
			}
		}
		return balance(parent);
	}

	private Node balance(Node p) {
		fixHeight(p);
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
		fixHeight(p);
		fixHeight(q);
		return q;
	}

	private Node rotateLeft(Node q) {
		Node p = q.right;
		q.right = p.left;
		p.left = q;
		fixHeight(q);
		fixHeight(p);
		return p;
	}

	private int height(Node p) {
		return p == null ? 0 : p.height;
	}

	private int bfactor(Node p) {
		return height(p.right) - height(p.left);
	}

	public double getMedian() {
		if (root == null) {
			throw new IllegalStateException("Can't get a median if the tree is empty");
		}
		if (size % 2 == 0) {
            int b = getNode(root, size / 2 - 1).key;
            int a = getNode(root, size / 2).key;
            return 0.5 * (a + b);
        } else {
            return getNode(root, size / 2).key;
        }
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
        // TODO: 
        return null;
    }

	private void fixHeight(Node p) {
		int hl = height(p.left);
		int hr = height(p.right);
		p.height = (hl > hr ? hl : hr) + 1;
	}

	public void insert(int... keys) {
		for (int key : keys) {
			root = insert(root, key);
		}
	}

	private static class Node {

		private Node left;
		private Node right;
		private final int key;
		private int height;

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