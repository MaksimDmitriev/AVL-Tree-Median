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
			root.mLeft.mChildCount++;
		} else {
			root.mRight = insert(root.mRight, value);
			root.mRight.mChildCount++;
		}
		return balance(root);
	}

	private Node balance(Node p) {
		if (getSubtreeSizeDiff(p) == 2) {
			if (getSubtreeSizeDiff(p.mRight) < 0) {
				p.mRight = rotateRight(p.mRight);
			}
			return rotateLeft(p);
		}

		if (getSubtreeSizeDiff(p) == -2) {
			if (getSubtreeSizeDiff(p.mLeft) > 0) {
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
		p.mChildCount = getRightChildCount(q) + 1;
		q.mChildCount += getRightChildCount(p);
		return q;
	}
	
	private int getLeftChildCount(Node node) {
		return node.mLeft == null ? -1 : node.mRight.mChildCount;
	}
	
	private int getRightChildCount(Node node) {
		return node.mRight == null ? -1 : node.mRight.mChildCount;
	}
	

	// Strictly speaking, it's not a balance factor
	private int getSubtreeSizeDiff(Node p) {
		// TODO: NPE
		return p.mRight.mChildCount - p.mLeft.mChildCount;
	}

	private static class Node {

		private Node mLeft;
		private Node mRight;
		private int mValue;
		private int mChildCount;

		Node(int value) {
			mValue = value;
		}

		@Override
		public String toString() {
			return Integer.toString(mValue) + " L:"
					+ (mLeft == null ? "0" : mLeft.mChildCount)
					+ (mRight == null ? "0" : mRight.mChildCount);
		}
	}

}
