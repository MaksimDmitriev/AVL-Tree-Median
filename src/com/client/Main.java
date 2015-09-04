package com.client;

import com.avl.AvlTree;

public class Main {
	
	public static void main(String[] args) {
		AvlTree avlTree = new AvlTree(3);
		avlTree.insert(1, 2, 4);
		int k = 4 / 2 - 1;
		System.out.println(avlTree.kthSmallest(avlTree.mRoot, k, true));
		
	}
}
