package com.sap.nic.mst;

import java.awt.Color;
import java.awt.print.Printable;

import edu.princeton.cs.algs4.AcyclicSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.introcs.Picture;



public class SeamCarverV1 {

    public static void main(String[] args) {
        Picture picture = new Picture("HJoceanSmall.png");
        SeamCarverV1 sCarver = new SeamCarverV1(picture);
        sCarver.show();
        sCarver.removeManyVertical(50);
        sCarver.removeManyHorizontal(50);
        sCarver.show();

    }
    
    public void show() {
        picture.show();
    }
    
    public void removeManyVertical(int times) {
        for(int i = 0; i < times; i++) {
            int[] a = findVerticalSeam();
            System.out.println(a.length);
//            removeVerticalSeam(a);
        }
    }
    
    public void removeManyHorizontal(int times) {
        for(int i = 0; i < times; i++) {
            int[] a = findHorizontalSeam();
            System.out.println(a.length);
//            removeHorizontalSeam(a);
        }
    }
    
    public SeamCarverV1(Picture picture) {
        this.picture = picture;
        this.energy = new double[picture.width()][picture.height()];
        
        this.start = 0;
        this.end = picture.width() * this.picture.height() + 1;
        computeEnergy();
        horizontalG = buildHorizontalGraph();
        verticalG = buildVerticalGraph();
        
    }
    
    private int[] calcHorizontalSeam() {
        
        int[] horizontalSeam = new int[picture.width()];
        AcyclicSP aSp = new AcyclicSP(horizontalG, start);
        if(aSp.hasPathTo(end)) {
            int i = 0;
            for(DirectedEdge edge :aSp.pathTo(end)) {
                if (i < picture.width())
                    horizontalSeam[i++] = getRowValue(edge.to());
            }
            
        }
        return horizontalSeam;
    }

    private int[] calcVerticalSeam() {
        
        int[] verticalSeam = new int[picture.height()];
        AcyclicSP aSp = new AcyclicSP(verticalG, start);
        if(aSp.hasPathTo(end)) {
            int i = 0;
            for(DirectedEdge edge :aSp.pathTo(end)) {
                if (i < picture.height())
                    verticalSeam[i++] = getColumnValue(edge.to());
            }
            
        }
        return verticalSeam;
    }
    
    private void computeEnergy() {
        for (int x = 0; x < this.picture.width(); x++) {
            for (int y = 0; y < this.picture.height(); y++) {
                if (isBorder(x, y)) {
                    this.energy[x][y] = ENERGY_OF_BORDER;
                } else {
                    this.energy[x][y] = xGradients(x, y) + yGradients(x, y);
                }
            }
        }
    }
    
    private double euclideanDistSquare(Color c1, Color c2) {
        return (Math.pow(c1.getRed() - c2.getRed(), 2) + Math.pow(c1.getBlue() - c2.getBlue(), 2) + Math.pow(c1.getGreen() - c2.getGreen(), 2));
    }
    
    private double xGradients(int x, int y ) {
        return euclideanDistSquare(picture.get(x + 1, y), picture.get(x - 1, y));
    }
    
    private double yGradients(int x, int y) {
        return euclideanDistSquare(picture.get(x, y + 1), picture.get(x, y - 1));
    }
    
    public Picture picture() {
        return picture;
    }
    
    public int width() {
        return picture.width();
    }
    
    public int height() {
        return picture.height();
    }
    
    public double energy(int x, int y) {
        if (!isIndexValid(x, y)) {
            throw new IndexOutOfBoundsException();
        }
        return this.energy[x][y];
    }
    
    public int[] findHorizontalSeam() {
        return calcHorizontalSeam();
       
    }
    
    public int[] findVerticalSeam() {
        return calcVerticalSeam();
        
    }
    

    public void removeHorizontalSeam(int[] a) {
        if (a.length != picture.width()) 
            throw new IllegalArgumentException();
        
        if (!isHorizontalSeamValid(a)) {
            throw new IllegalArgumentException();
        }
        
        Picture newPicture = new Picture(picture.width(), picture.height() - 1);
        
        for (int x = 0; x < a.length; x++) {
            for(int y = 0; y < a[x]; y++) {
                newPicture.set(x, y, picture.get(x, y));
            }
            
            for (int y = a[x]; y < newPicture.height(); y++) {
                newPicture.set(x, y, picture.get(x, y + 1));
            }
        }
        picture = newPicture;
  
        start = 0;
        this.end = picture.height() * picture.width() + 1;
        horizontalG = buildHorizontalGraph();
        computeEnergy();

    }
    
    private boolean isHorizontalSeamValid(int[] a) {
        for(int i = 1; i < a.length; i ++) {
            if (Math.abs(a[i] - a[i - 1]) > 1) {
                return false;
            }
            if (a[i] < 0 || a[i] >= picture.height()) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isVerticalSeamValid(int[] a) {
        for(int i = 1; i < a.length; i ++) {
            if (Math.abs(a[i] - a[i - 1]) > 1) {
                return false;
            }
            if (a[i] < 0 || a[i] >= picture.width()) {
                return false;
            }
        }
        
        return true;
    }
    public void removeVerticalSeam(int[] a) {
        if (a.length != picture.height()) 
            throw new IllegalArgumentException();
        
        if (!isVerticalSeamValid(a)) {
            throw new IllegalArgumentException();
        }
        
        Picture newPicture = new Picture(picture.width() - 1, picture.height());
        
        for (int y = 0; y < a.length; y++) {
            for(int x = 0; x < a[y]; x++) {
                newPicture.set(x, y, picture.get(x, y));
            
            }
            for(int x = a[y]; x < newPicture.width(); x++) {
                newPicture.set(x, y, picture.get(x + 1, y));
            }
        }

        
        picture = newPicture;
        start = 0;
        this.end = picture.height() * picture.width() + 1;
        verticalG = buildVerticalGraph();
        computeEnergy();
    }
    
    private boolean isBorder(int x, int y) {
        if (x == 0 || x == picture.width() - 1) return true;
        if (y == 0 || y == picture.height() - 1) return true;
        return false;
    }
    
    // convert coordinate (x,y) to 1d array index
    private int getVertexIndex(int x, int y ) {
        return y * picture.width() + x + 1;
    }
    
    private int getColumnValue(int v) {
        return (v - 1) % picture.width();
    }
    
    private int getRowValue(int v) {
        return (v - 1) / picture.width();
    }
    
    private boolean isIndexValid(int x, int y) {
        if ( x >=0 && x < picture.width() && y >= 0 && y < picture.height()) {
            return true;
        }
        return false;
    }
   
     
    // Bulid an graph using start point at the first row (x, 0)
    private EdgeWeightedDigraph buildVerticalGraph() {
        
        EdgeWeightedDigraph dgraph = new EdgeWeightedDigraph(picture.width() * picture.height() + 2);

        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                if (isIndexValid(x + 1, y + 1)) {
                    dgraph.addEdge(new DirectedEdge(getVertexIndex(x, y), getVertexIndex(x + 1, y  + 1), energy(x + 1, y + 1)));
                }
                
                if (isIndexValid(x, y + 1)) {
                    dgraph.addEdge(new DirectedEdge(getVertexIndex(x, y), getVertexIndex(x, y  + 1), energy(x, y + 1)));
                }

                if (isIndexValid(x - 1, y + 1)) {
                    dgraph.addEdge(new DirectedEdge(getVertexIndex(x, y), getVertexIndex(x - 1, y  + 1), energy(x - 1, y + 1)));
                }

            }
        }
        
        // Add edge from start fake point to all those top points
        for (int x = 0; x < picture.width(); x++) {
            dgraph.addEdge(new DirectedEdge(start, getVertexIndex(x, 0), energy(x, 0)));
        }
        
        //Add edge from bottom point to the fake end point
        for (int x  = 0; x < picture.width(); x++) {
            dgraph.addEdge(new DirectedEdge(getVertexIndex(x, picture.height() - 1), end, 0));
        }
        
        return dgraph;
    }
    
    private EdgeWeightedDigraph buildHorizontalGraph() {
        EdgeWeightedDigraph dgraph = new EdgeWeightedDigraph(picture.width() * picture.height() + 2);

        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                if (isIndexValid(x + 1, y )) {
                    dgraph.addEdge(new DirectedEdge(getVertexIndex(x, y), getVertexIndex(x + 1, y), energy(x + 1, y)));
                }
                
                if (isIndexValid(x + 1, y - 1)) {
                    dgraph.addEdge(new DirectedEdge(getVertexIndex(x, y), getVertexIndex(x + 1, y - 1), energy(x + 1, y - 1)));
                }

                if (isIndexValid(x + 1, y + 1)) {
                    dgraph.addEdge(new DirectedEdge(getVertexIndex(x, y), getVertexIndex(x + 1, y + 1), energy(x + 1, y + 1)));
                }

            }
        }
        
        // Add edge from start fake point to all those top points
        for (int y = 0; y < picture.height(); y++) {
            dgraph.addEdge(new DirectedEdge(start, getVertexIndex(0, y), energy(0, y)));
        }
        
        //Add edge from bottom point to the fake end point
        for (int y  = 0; y < picture.height(); y++) {
            dgraph.addEdge(new DirectedEdge(getVertexIndex(picture.width() - 1, y), end, 0));
        }
        
        return dgraph;
    }


    private final static double ENERGY_OF_BORDER = 195075;
    
    private Picture picture;
    private double[][] energy;
    private int start;
    private int end;
    
    private EdgeWeightedDigraph horizontalG;
    private EdgeWeightedDigraph verticalG;

}
