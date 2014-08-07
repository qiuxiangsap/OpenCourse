package com.sap.nic.kdtree;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;

public class PointSET {
	// construct an empty set of points
	  public PointSET() {
		  points = new SET<Point2D>();
	  }
	   // is the set empty?
	   public boolean isEmpty()  {
		   return points.isEmpty();
	   }
	   // number of points in the set
	   public int size() {
		   return points.size();
	   }
	   // add the point p to the set (if it is not already in the set)
	   public void insert(Point2D p) {
		   points.add(p);
	   }
	   // does the set contain the point p?
	   public boolean contains(Point2D p)  {
		   return contains(p);
	   }
	   
	   // draw all of the points to standard draw
	   public void draw() {
		   for (Point2D p:points) {
			   p.draw();
		   }
	   }
	   
	   // all points in the set that are inside the rectangle
	   public Iterable<Point2D> range(RectHV rect) {
		   ArrayList<Point2D> res = new ArrayList<Point2D>();
		   for (Point2D p:points) {
			   if (rect.contains(p)) {
				   res.add(p);
			   }
		   }
		   return res;
	   }
	   // a nearest neighbor in the set to p; null if set is empty
	   public Point2D nearest(Point2D p) {
		   double minDist = Double.MIN_VALUE;
		   Point2D closest = null;
		   
		   for (Point2D that:points) {
			   double dist = p.distanceTo(that);
			   if (dist < minDist) {
				   minDist = dist;
				   closest = that;
			   }
		   }
		   return closest;
	   }
	   
	   private SET<Point2D> points;
	   

}
