package com.avl;

public class AvlTree {

	private Node mRoot;

	public AvlTree(int... values) {
		insert(values);
	}

	public void insert(int... values) {
		for (int value : values) {
			mRoot = insert(mRoot, value);
		}
	}

	private Node insert(Node root, int value) {
		if (root == null) {
			return new Node(value);
		}
		if (value < root.mValue) {
			root.mLeft = insert(root.mLeft, value);
		} else {
			root.mRight = insert(root.mRight, value);
		}
		return balance(root);
	}	

	private Node balance(Node p) {
		fixHeight(p);

		if (getBalanceFactor(p) == 2) {
			if (getBalanceFactor(p.mRight) < 0) {
				p.mRight = rotateRight(p.mRight);
			}
			return rotateLeft(p);
		}

		if (getBalanceFactor(p) == -2) {
			if (getBalanceFactor(p.mLeft) > 0) {
				p.mLeft = rotateLeft(p.mLeft);
			}
			return rotateRight(p);
		}

		return p;
	}

	private Node rotateLeft(Node q) {
		Node p = q.mRight;
		q.mRight = p.mLeft;
		p.mLeft = q;
		fixHeight(q);
		fixHeight(p);
		return p;
	}

	private Node rotateRight(Node p) {
		Node q = p.mLeft;
		p.mLeft = q.mRight;
		q.mRight = p;
		fixHeight(p);
		fixHeight(q);
		return q;
	}

	private int getBalanceFactor(Node p) {
		return getHeight(p.mRight) - getHeight(p.mLeft);
	}

	private void fixHeight(Node p) {
		int left = getHeight(p.mLeft);
		int right = getHeight(p.mRight);
		p.mHeight = Math.max(left, right) + 1;
	}

	private int getHeight(Node p) {
		return p == null ? 0 : p.mHeight;
	}

	private static class Node {

		private Node mLeft;
		private Node mRight;
		private int mValue;
		private int mHeight;

		Node(int value) {
			mValue = value;
			mHeight = 1;
		}

		@Override
		public String toString() {
			return Integer.toString(mValue) + ":" + Integer.toString(mHeight);
		}
	}

}
