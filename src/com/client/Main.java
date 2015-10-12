package com.client;

import java.util.Map;
import java.util.TreeMap;

import com.avl.AvlTree;

public class Main {

	public static void main(String[] args) {
		Map<A, Integer> map = new TreeMap<>();
		map.put(new A(), 1);
	} 
	
	static class A {}
	
	private static void runWithAvl() {
		AvlTree avlTree = new AvlTree();
		for (int i = 0; i < 10000000; i++) {
			avlTree.insert(i);
		}
		long start = System.currentTimeMillis();
		avlTree.getMedian();
		printDuration("AvlTree ", start, System.currentTimeMillis());
	}
	
	private static void printDuration(String operation, long start, long end) {
		System.out.println(operation + (end - start) / 1000.0);
	}
}
