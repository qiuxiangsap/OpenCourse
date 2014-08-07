package com.sap.nic.graph;

import java.util.LinkedList;
import java.util.Queue;

import org.omg.CORBA.INTERNAL;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class SAP {

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!isIndexValid(v)) {
            throw new IndexOutOfBoundsException();
        }

        int[] distToA = shortestPath(v, digraph);
        int[] distToB = shortestPath(w, digraph);
        
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < distToA.length; i++) {
            if (distToA[i] < Integer.MAX_VALUE && distToB[i] < Integer.MAX_VALUE) {
                if (distToA[i] + distToB[i] < minDist) {
                    minDist = distToA[i] + distToB[i];
                }
            }
        }
        
        if (minDist == Integer.MAX_VALUE) {
            return -1;
        }
        return minDist;
    }

    private int[] shortestPath(int start, Digraph digraph) {
        boolean[] marked = new boolean[digraph.V()];
        int[] dist = new int[digraph.V()];
        
        for(int i = 0;  i < dist.length; i++) {
            dist[i] = Integer.MAX_VALUE;
            
        }
        
        Queue<Integer> queue = new LinkedList<Integer>();
        marked[start] = true;
        dist[start] = 0;
        queue.add(start);
        
        while(!queue.isEmpty()) {
            int current = queue.poll();
            
            for (int v:digraph.adj(current)) {
                if (!marked[v]) {
                    marked[v] = true;
                    dist[v] = dist[current] + 1;
                    queue.add(v);
                }
            }
        }
        
        return dist;
     }
    
    // a common ancestor of v and w that participates in a shortest ancestral
    // path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isIndexValid(v)) {
            throw new IndexOutOfBoundsException();
        }

        if (!isIndexValid(w)) {
            throw new IndexOutOfBoundsException();
        }

        if (v == w) {
            return v;
        }
        
        int[] distToA = shortestPath(v, digraph);
        int[] distToB = shortestPath(w, digraph);
        
        int minDist = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < distToA.length; i++) {
            if (distToA[i] < Integer.MAX_VALUE && distToB[i] < Integer.MAX_VALUE) {
                if (distToA[i] + distToB[i] < minDist) {
                    minDist = distToA[i] + distToB[i];
                    ancestor = i;
                }
            }
        }
        
        if (ancestor == - 1) {
            return -1;
        } else {
            return ancestor;
        }
        
    }

    // length of shortest ancestral path between any vertex in v and any vertex
    // in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isIndexValid(v)) {
            throw new IndexOutOfBoundsException();
        }

        if (!isIndexValid(w)) {
            throw new IndexOutOfBoundsException();
        }

        int[] marked = new int[digraph.V()];
        int[] dist = new int[digraph.V()];
        int[] root = new int[digraph.V()];

        Queue<Integer> queue = new LinkedList<Integer>();

        for (int node : v) {
            root[node] = 1; // denote root node at set v
            queue.add(node);
            marked[node] = 1;
        }

        for (int node : w) {
            root[node] = 2; // denote root node at set w
            queue.add(node);
            marked[node] = 2;
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int nebor : digraph.adj(current)) {
                if (marked[nebor] == 0) {
                    marked[nebor] = root[current];
                    queue.add(nebor);
                    dist[nebor] = dist[current] + 1;
                } else if (marked[nebor] != marked[current]) {
                    return (dist[nebor] + dist[current] + 1);
                } else {
                    if (dist[nebor] > dist[current] + 1) {
                        dist[nebor] = dist[current] + 1;
                    }
                }
            }
        }

        return -1;

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no
    // such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!isIndexValid(v)) {
            throw new IndexOutOfBoundsException();
        }

        if (!isIndexValid(w)) {
            throw new IndexOutOfBoundsException();
        }

        int[] marked = new int[digraph.V()];
        int[] dist = new int[digraph.V()];
        int[] root = new int[digraph.V()];

        Queue<Integer> queue = new LinkedList<Integer>();

        for (int node : v) {
            root[node] = 1; // denote root node at set v
            queue.add(node);
            marked[node] = 1;
        }

        for (int node : w) {
            root[node] = 2; // denote root node at set w
            queue.add(node);
            marked[node] = 2;
        }

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int nebor : digraph.adj(current)) {
                if (marked[nebor] == 0) {
                    queue.add(nebor);
                    marked[nebor] = marked[current];
                    dist[nebor] = dist[current] + 1;
                } else if (marked[nebor] != marked[current]) {
                    return nebor;
                }
            }
        }

        return -1;

    }

    private boolean isIndexValid(int v) {
        if (v >= 0 && v < digraph.V()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isIndexValid(Iterable<Integer> v) {
        for (int w : v) {
            if (!isIndexValid(w)) {
                return false;
            }
        }
        return true;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

    }

    private Digraph digraph;
}
