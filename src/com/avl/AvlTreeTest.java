package com.avl;

import org.junit.Assert;
import org.junit.Test;

public class AvlTreeTest {

	private static final double DELTA = 1e-15;

	@Test(expected = IllegalArgumentException.class)
	public void testNull() {
		AvlTree avlTree = new AvlTree(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmpty() {
		AvlTree avlTree = new AvlTree();
	}


	@Test
	public void testSingle() {
		AvlTree avlTree = new AvlTree(1);
		Assert.assertEquals(1.0, avlTree.getMedian(), DELTA);
	}

	@Test
	public void testTwo_OnlyLeft() {
		AvlTree avlTree = new AvlTree(3, 2);
		Assert.assertEquals(2.5, avlTree.getMedian(), DELTA);
	}

	@Test
	public void testTwo_OnlyRight() {
		AvlTree avlTree = new AvlTree(5, 7);
		Assert.assertEquals(6.0, avlTree.getMedian(), DELTA);
	}

	@Test
	public void testRootAndSuccessor() {
		AvlTree avlTree = new AvlTree(5, 2, 7, 1, 6, 3, 8, 9);
		Assert.assertEquals(5.5, avlTree.getMedian(), DELTA);
	}
	
	@Test
	public void testPredecessorAndRoot() {
		AvlTree avlTree = new AvlTree(5, 2, 7, 1, 6, 3, 8, 4);
		Assert.assertEquals(4.5, avlTree.getMedian(), DELTA);
	}

	@Test
	public void testGoRightEven() {
		AvlTree avlTree = new AvlTree(5, 2, 9, 1, 3, 7, 12, 8, 11, 13);
		Assert.assertEquals(7.5, avlTree.getMedian(), DELTA);
	}

	@Test
	public void testGoLeftEven() {
		AvlTree avlTree = new AvlTree(6, 3, 9, 1, 4, 7, 12, 0, 2, 5);
		Assert.assertEquals(4.5, avlTree.getMedian(), DELTA);
	}
	
	@Test
	public void testGoRightOdd() {
		AvlTree avlTree = new AvlTree(10, 4, 14, 2, 5, 12, 17, 1, 3, 7, 11, 13, 16, 19, 15, 18, 20);
		Assert.assertEquals(12.0, avlTree.getMedian(), DELTA);
	}

	@Test
	public void testGoLeftOdd() {
	}

}
