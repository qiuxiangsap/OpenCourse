package com.sap.nic.kdtree;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;

public class KdTree {
	// construct an empty set of points
	public KdTree() {
		root = null;
	}

	// is the set empty?
	public boolean isEmpty() {
		return root == null;
	}

	// number of points in the set
	public int size() {
		if ( root == null ) {
			return 0;
		} else {
			return root.size;
		}
	}

	private Node insert(Node node, Point2D p, int mode, RectHV rect) {
		if ( node == null ) {
			node = new Node(p, null, null, mode, rect);
			return node;
		}
		
		int cur_mode = (mode == 0? 1:0);
		
		if ( node.point.compareTo(p) == 0 ) {
			return node;
		}
		
		if ( (mode == 0 && node.point.x() > p.x() )) {
			RectHV rectLeft = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
			node.left = insert(node.left, p, cur_mode, rectLeft);
		} else if ( (mode == 0) && node.point.x() <= p.x() ) {
			RectHV rectRight = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
			node.left = insert(node.left, p, cur_mode, rectRight);
		} else if ( (mode == 1) && node.point.y() > p.y()) {
			RectHV rectDown = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
			node.left = insert(node.left, p, cur_mode, rectDown);
		} else {
			RectHV rectUp = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
			node.left = insert(node.left, p, cur_mode, rectUp);
		}
		
		
		return node;
	}
	
	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		RectHV rect = new RectHV(0, 0, 1, 1);
		root = insert(root, p, 0, rect);
	}

	// does the set contain the point p?
	public boolean contains(Point2D p) {
		return search(root, p, 0);
	}
	
	private boolean search(Node node, Point2D p, int mode) {
		if ( node == null ) {
			return false;
		}
		
		if (node.point.compareTo(p) == 0) {
			return true;
		}
		
		int next_mode = (mode == 0?1:0);
		
		if ( (mode == 0 && node.point.x() > p.x()) || ( mode == 1 && node.point.y() > p.y()) ) {
			return search(node.left, p, next_mode);
		} else {
			return search(node.right, p, next_mode);
		}
	}

	// draw all of the points to standard draw
	public void draw() {

	}

	// all points in the set that are inside the rectangle
	public Iterable<Point2D> range(RectHV rect) {
		ArrayList<Point2D> res = new ArrayList<Point2D>();
		searchRange(root, rect, res);
		return res;
	}
	
	public void searchRange(Node node, RectHV rect, ArrayList<Point2D> res) {
		if (node == null) return;
		if ( rect.contains(node.point) ) {
			res.add(node.point);
		}
			
		if ( node.rect.intersects(rect) == false ) {
			return;
		}
		searchRange(node.left, rect, res);
		searchRange(node.right, rect, res);
		
	}

	// a nearest neighbor in the set to p; null if set is empty
	public Point2D nearest(Point2D p) {
		MaxDistNode mNode = new MaxDistNode(Double.MAX_VALUE, null);
		
		return null;
	}
	
	private void nearest(Node node, Point2D p, MaxDistNode res) {
		if (node == null) {
			return;
		}
		
		double dist = p.distanceTo(node.point);
		if ( dist < res.getDist() ) {
			res.setDist(dist);
			res.setMaxDistNode(node.point);
		}
		
	}

	class MaxDistNode {
		private double dist;
		Point2D maxNode;
		
		public MaxDistNode(double dist, Point2D maxNode) {
			super();
			this.dist = dist;
			this.maxNode = maxNode;
		}
		
		public void setDist(double dist) {
			this.dist = dist;
		}
		
		public void setMaxDistNode(Point2D maxNode) {
			this.maxNode = maxNode;
		}
		
		public Point2D getMaxDistNode() {
			return maxNode;
		}
		
		public double getDist() {
			return dist;
		}
	}
	
	private Node root;

	class Node {

		Point2D point;
		private RectHV rect;
		Node left, right;
		int mode; // mode == 0 means compare using x coordinate , 1 means using y coordinate
		int size; // number of nodes rooted at the node

		public Node(Point2D point, Node left, Node right, int mode, RectHV rect) {
			this.point = point;
			this.left = left;
			this.right = right;
			this.mode = mode;
			this.rect = rect;
		}
	
	}
	
	public static void main(String[] args) {
		KdTree kdTree = new KdTree();
		kdTree.insert(new Point2D(0.7, 0.2));
		kdTree.insert(new Point2D(0.5, 0.4));
		kdTree.insert(new Point2D(0.2, 0.3));
		kdTree.insert(new Point2D(0.4, 0.7));
		kdTree.insert(new Point2D(0.9, 0.6));	
		
		System.out.println(kdTree.contains(new Point2D(0.5, 0.4)));
		RectHV rectHV = new RectHV(0, 0, 1, 1);
		System.out.println(kdTree.range(rectHV));
	}

}
