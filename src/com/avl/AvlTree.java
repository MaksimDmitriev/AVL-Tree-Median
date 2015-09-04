package com.avl;

import java.awt.image.SampleModel;
import java.util.Deque;
import java.util.LinkedList;


public class AvlTree {

	public Node mRoot;

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
    	// Let's handle the simplest case
    	if (leftChildCount == rightChildCount) {
    		return mRoot.mValue;
    	}
    	int elementCount = leftChildCount + rightChildCount + 1;
    	boolean treeHasEvenNodes = elementCount % 2 == 0;
    	if (leftChildCount < rightChildCount) {
    		
    	} else {
    		
    	}
    	return 0;
    	
    }
    
    public double kthSmallest(Node root, int k, boolean even) {
    	/*
    	 * I chose LinkedList rather than ArrayDeque because LinkedList offers constant time 
    	 * for delete() and insert(). pop() calls removeFirst(), and push(e) calls addFirst(e).
    	 * 
    	 * However, if I understand the answer on http://stackoverflow.com/a/249695/1065835 correctly, 
    	 * the difference between constant time and amortized constant time is little if we perform 
    	 * the operation many times.
    	 */
    	Deque<Node> stack = new LinkedList<>();
    	Node current = root;
    	int i = 0;
    	double smallest = current.mValue;
    	while(true) {
    		if (current != null) {
        		stack.push(current);
        		current = current.mLeft;	
    		} else {
    			Node last = stack.pop();
    			if (i == k) {
    				smallest = last.mValue;
    			}
    			if (!even) {
    				break;
    			}
    			if (even & i == k + 1) {
    				smallest += last.mValue;
    				smallest /= 2.0;
    				break;
    			}
    			i++;
    			current = last.mRight;
    		}
    	}
    	return smallest;
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
            return Integer.toString(mValue);
        }
    }
	
	static class MedianHelper {
		
		double value;
	}
}