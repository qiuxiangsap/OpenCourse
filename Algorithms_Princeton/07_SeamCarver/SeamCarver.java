package com.sap.nic.mst;


import java.awt.Color;

import edu.princeton.cs.introcs.Picture;



public class SeamCarver {

    public static void main(String[] args) {
        Picture picture = new Picture("HJoceanSmall.png");
//        Picture picture = new Picture("6x5.png");
        SeamCarver sCarver = new SeamCarver(picture);
        sCarver.show();
        sCarver.removeManyVertical(100);
        sCarver.removeManyHorizontal(40);
        sCarver.show();

    }
    
    public void show() {
        picture.show();
    }
    
    public void removeManyVertical(int times) {
        for(int i = 0; i < times; i++) {
            int[] a = findVerticalSeam();
//            System.out.println(a.length);
            removeVerticalSeam(a);
        }
    }
    
    public void removeManyHorizontal(int times) {
        for(int i = 0; i < times; i++) {
            int[] a = findHorizontalSeam();
            removeHorizontalSeam(a);
        }
    }
    
    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.energy = new double[picture.width()][picture.height()];
       
        computeEnergy();
        
    }
    
    private int[] calcHorizontalSeam() {
        int width = picture.width();
        int height = picture.height();
        
        double[][] distTo = new double[width][height];
        int[][] edgeTo = new int[width][height];
        
        for (int y = 0; y < height; y++) {
            distTo[0][y] = energy(0, y);
        }
        
        for (int x = 1; x < width; x++) {
            for(int y = 0; y < height; y++) {
                distTo[x][y] = Double.MAX_VALUE;
            }
        }
                
        for (int x = 0; x < width - 1; x++) {
            for(int y = 0; y < height; y++) {
                if (isIndexValid(x + 1, y + 1)) {
                    if (distTo[x + 1][ y + 1] > distTo[x][y] + energy(x + 1, y + 1)) {
                        distTo[x + 1][ y + 1] = distTo[x][y] + energy(x + 1, y + 1);
                        edgeTo[x + 1][y + 1] = getVertexIndex(x, y);
                    }
                    
                }
                
                if (isIndexValid(x + 1, y )) {
                    if (distTo[x + 1][ y] > distTo[x][y] + energy(x + 1, y)) {
                        distTo[x + 1][ y] = distTo[x][y] + energy(x + 1, y);
                        edgeTo[x + 1][ y] = getVertexIndex(x, y);
                    }
                    
                }
                
                if (isIndexValid(x + 1, y - 1)) {
                    if (distTo[x + 1][ y - 1] > distTo[x][y] + energy(x + 1, y - 1)) {
                        distTo[x + 1][ y - 1] = distTo[x][y] + energy(x + 1, y - 1);
                        edgeTo[x + 1][ y - 1] = getVertexIndex(x, y);
                    }
                }
            }
        }
        
        double min = distTo[width - 1][0];
        int minIndex = 0;
        for (int y = 1; y < height; y++ ) {
            if (distTo[width-1][y] < min) {
                minIndex = y;
                min = distTo[width - 1][y];
            }
        }
        
        int[] vSeam = new int[width];
        vSeam[width - 1] = minIndex;
        
        int yIndex = minIndex;
        int xIndex = width - 1;
        for (int x = width - 2; x >= 0 ; x--) {
            int node = edgeTo[xIndex][yIndex];
             xIndex = getX(node);
             yIndex = getY(node);
             vSeam[x] = yIndex;
        }
        
        return vSeam;
        
    }

    
    private int[] calVerticalSeamFaster() {
        int width = picture.width();
        int height = picture.height();
        
        double[][] distTo = new double[width][height];
        int[][] edgeTo = new int[width][height];
        
        for (int x = 0; x < width; x++) {
            distTo[x][0] = energy(x, 0);
        }
        
        for (int x = 0; x < width; x++) {
            for(int y = 1; y < height; y++) {
                distTo[x][y] = Double.MAX_VALUE;
            }
        }
                
        for (int y = 0; y < height - 1; y++) {
            for(int x = 0; x < width; x++) {
                if (isIndexValid(x + 1, y + 1)) {
                    if (distTo[x + 1][ y + 1] > distTo[x][y] + energy(x + 1, y + 1)) {
                        distTo[x + 1][ y + 1] = distTo[x][y] + energy(x + 1, y + 1);
                        edgeTo[x + 1][y + 1] = getVertexIndex(x, y);
                    }
                    
                }
                
                if (isIndexValid(x, y + 1)) {
                    if (distTo[x][ y + 1] > distTo[x][y] + energy(x, y + 1)) {
                        distTo[x][ y + 1] = distTo[x][y] + energy(x, y + 1);
                        edgeTo[x][y + 1] = getVertexIndex(x, y);
                    }
                    
                }
                
                if (isIndexValid(x - 1, y + 1)) {
                    if (distTo[x - 1][ y + 1] > distTo[x][y] + energy(x - 1, y + 1)) {
                        distTo[x - 1][ y + 1] = distTo[x][y] + energy(x - 1, y + 1);
                        edgeTo[x - 1][y + 1] = getVertexIndex(x, y);
                    }
                }
            }
        }
        
        double min = distTo[0][height - 1];
        int minIndex = 0;
        for (int x = 1; x < width; x++ ) {
            if (distTo[x][height - 1] < min) {
                minIndex = x;
                min = distTo[x][height - 1];
            }
        }
        
        int[] vSeam = new int[height];
        vSeam[height - 1] = minIndex;
        
        int xIndex = minIndex;
        int yIndex = height - 1;
        for (int y = height - 2; y >= 0 ; y--) {
            int node = edgeTo[xIndex][yIndex];
             xIndex = getX(node);
             yIndex = getY(node);
             vSeam[y] = xIndex;
        }
        return vSeam;
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
        return calVerticalSeamFaster();
        
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
        this.energy = new double[picture.width()][picture.height()];
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
        this.energy = new double[picture.width()][picture.height()];
        computeEnergy();
    }
    
    private boolean isBorder(int x, int y) {
        if (x == 0 || x == picture.width() - 1) return true;
        if (y == 0 || y == picture.height() - 1) return true;
        return false;
    }
    
    // convert coordinate (x,y) to 1d array index
    private int getVertexIndex(int x, int y ) {
        return y * picture.width() + x ;
    }
    
    private int getX(int v) {
        return v % picture.width();
    }
    
    private int getY(int v) {
        return v / picture.width();
    }
    
    private boolean isIndexValid(int x, int y) {
        if ( x >=0 && x < picture.width() && y >= 0 && y < picture.height()) {
            return true;
        }
        return false;
    }
   
     



    private final static double ENERGY_OF_BORDER = 195075;
    
    private Picture picture;
    private double[][] energy;


}
