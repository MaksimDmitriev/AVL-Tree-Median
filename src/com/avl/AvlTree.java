package com.avl;


public class AvlTree {

    Node mRoot;

    public AvlTree() {

    }

    public AvlTree(int... keys) {
    	insert(keys);
    }

    private Node insert(Node parent, int key) {
        if (parent == null) {
            return new Node(key);
        }
        if (key < parent.mValue) {
            parent.mLeft = insert(parent.mLeft, key);
        } else {
            parent.mRight = insert(parent.mRight, key);
        }
        return balance(parent);
    }

    private Node balance(Node p) {
        fixHeightAndChildCount(p);
        if (bfactor(p) == 2) {
            if (bfactor(p.mRight) < 0) {
                p.mRight = rotateRight(p.mRight);
            }
            return rotateLeft(p);
        }
        if (bfactor(p) == -2) {
            if (bfactor(p.mLeft) > 0) {
                p.mLeft = rotateLeft(p.mLeft);
            }
            return rotateRight(p);
        }
        return p;
    }
    
    private Node rotateRight(Node p) {
        Node q = p.mLeft;
        p.mLeft = q.mRight;
        q.mRight = p;
        fixHeightAndChildCount(p);
        fixHeightAndChildCount(q);
        return q;
    }
    
    private Node rotateLeft(Node q) {
        Node p = q.mRight;
        q.mRight = p.mLeft;
        p.mLeft = q;
        fixHeightAndChildCount(q);
        fixHeightAndChildCount(p);
        return p;
    }

    private int height(Node p) {
        return p == null ? 0 : p.mHeight;
    }
    
    private int bfactor(Node p) {
        return height(p.mRight) - height(p.mLeft);
    }
    
    public double getMedian() {
    	if (mRoot == null) {
    		return -1;
    	}
    	int leftChildCount = mRoot.mLeft == null ? 0 : mRoot.mLeft.mChildCount;
    	int rightChildCount = mRoot.mRight == null ? 0 : mRoot.mRight.mChildCount;
    	if (leftChildCount == rightChildCount) {
    		return mRoot.mValue;
    	}
    	MedianHelper medianHelper = new MedianHelper();
    	final int elementCount = mRoot.mChildCount + 1;
    	getMedian(mRoot, 0, elementCount, elementCount % 2 == 0, medianHelper);
    	return medianHelper.value;
    }
    
    private int getMedian(Node root, int index, int elementCount, boolean even, MedianHelper medianHelper) {
    	if (root == null) {
    		return index;
    	}
		index = getMedian(root.mLeft, index, elementCount, even, medianHelper);
		if (even) {
			if (index == elementCount / 2 - 1) {
				medianHelper.value = root.mValue;
			} else if (index == elementCount / 2) {
				medianHelper.value += root.mValue;
				medianHelper.value /= 2;
			}
		} else if (index == elementCount / 2) {
			medianHelper.value = root.mValue;
		}
		index++;
		index = getMedian(root.mRight, index, elementCount, even, medianHelper);
    	return index;
    }

    private void fixHeightAndChildCount(Node p) {
        int hl = height(p.mLeft);
        int hr = height(p.mRight);
        p.mHeight = (hl > hr ? hl : hr) + 1;
        p.mChildCount = 0;
        if (p.mLeft != null) {
        	p.mChildCount = p.mLeft.mChildCount + 1;
        }
        if (p.mRight != null) {
        	p.mChildCount += p.mRight.mChildCount + 1;
        }
    }
    
    public int foo() {
    	return foo(mRoot, 0);
    }
    
    private int foo(Node root, int i) {
    	if (root == null) {
    		return i;
    	}
    	i = foo(root.mLeft, i);
    	++i;
    	i = foo(root.mRight, i);
    	return i;
    }

    public void insert(int... keys) {
        for (int value : keys) {
            mRoot = insert(mRoot, value);
        }
    }

	@Override
	public String toString() {
		if (mRoot == null) {
			return "[]";
		}
		StringBuilder builder = new StringBuilder("[");
		inOrderPrint(mRoot, builder);
		builder.setLength(builder.length() - 2);
		builder.append("]");
		return builder.toString();
	}

	private void inOrderPrint(Node root, StringBuilder builder) {
		if (root != null) {
			inOrderPrint(root.mLeft, builder);
			builder.append(root + ", ");
			inOrderPrint(root.mRight, builder);
		}
	}

	static class Node {

        Node mLeft;
        Node mRight;
        final int mValue;
        private int mHeight;
        private int mChildCount;

        private Node(int value) {
            mValue = value;
            mHeight = 1;
        }

        @Override
        public int hashCode() {
            int res = 17;
            res = 17 * res + mValue;
            return res;
        }

        @Override
        public String toString() {
            return Integer.toString(mValue) + " cc=" + mChildCount;
        }
    }
	
	static class MedianHelper {
		
		double value;
	}
}