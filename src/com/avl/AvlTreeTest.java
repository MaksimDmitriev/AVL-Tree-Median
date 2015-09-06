package com.avl;

import org.junit.Assert;
import org.junit.Test;

public class AvlTreeTest {

	private static final double DELTA = 1e-15;

	@Test
	public void testEmpty() {
		AvlTree avlTree = new AvlTree();
		Assert.assertEquals(-1.0, avlTree.getMedian(), DELTA);
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
	public void testGoRight() {
		AvlTree avlTree = new AvlTree(5, 2, 7, 1, 6, 3, 8, 9, 10);
		Assert.assertEquals(6.0, avlTree.getMedian(), DELTA);
	}

	@Test
	public void testGoLeft() {
		AvlTree avlTree = new AvlTree(5, 1, 7, 0, 6, 3, 9, 2, 4);
		Assert.assertEquals(4.0, avlTree.getMedian(), DELTA);
	}

}
