/**
 * @author Zhongcun Wang
 * @date May 27th, 2014
 */

package com.sap.nic.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class BoggleSolver {
    
    private Set<String> words;
    private Node root;
    private static final int R = 256;

    public static void main(String[] args) {
        In in = new In("dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("board4x4.txt");
        
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);

    }
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        words = new HashSet<String>();
        for (String word:dictionary) {
            if (word.length() >= 3 && !containsCharMoreThanOnes(word)) {
                words.add(word);
            }
        }
        
        for (String word:words) {
            put(word, 1);
        }
        
        
    }
    
    private static class Node {
        private int val;
        private Node[] next = new Node[R];
    }

    private boolean isInBoard(int x, int y, int nrow, int ncol) {
        if (x >= 0 && x <ncol && y >= 0 && y <= nrow - 1) {
            return true;
        }
        return false;
    }
    
    private void put(String key, int val) {
        root = put(root, key, val, 0);
    }
    

    private Node put(Node x, String key, int val, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, val, d+1);
        return x;
    }
    
    private int toOneDim(int x, int y,  int ncol) {
        return y * ncol + x;
    }

    
    private void collect(Node x, StringBuilder prefix, Queue<String> results, HashMap<Character, ArrayList<Integer> > position, Digraph digraph, String pathEnds) {
       if (x == null) {
           return;
       }
       
       if (x.val != 0) results.add(prefix.toString());
       String dupPathEnds = pathEnds;
       for (char c = 0; c < R; c++) {
           
           if (prefix.length() > 0){
               char lastChar = prefix.charAt(prefix.length() - 1);
               if (!isPathExistBetween(lastChar, c, digraph, position, pathEnds)) {
                   continue;
               }
           }
           
           prefix.append(c);
           
           collect(x.next[c], prefix, results, position, digraph, pathEnds);
           prefix.deleteCharAt(prefix.length() - 1);
           pathEnds.clear();
           pathEnds.addAll(dupPathEnds);
       }
    }
    
    //check whether there exists an path between lastChar and c
    private boolean isPathExistBetween(char lastChar, char c, Digraph digraph, HashMap<Character, ArrayList<Integer> > position, String pathEnds) {
        ArrayList<Integer> pos_a = position.get(lastChar);
        ArrayList<Integer> pos_b = position.get(c);
        boolean res = false;
        
        if (pos_a == null || pos_b == null) {
            return false;
        }
        if (pathEnds.size() == 0) {
            for (int i = 0; i < pos_a.size(); i++) {
                for(int j = 0; j < pos_b.size(); j++) {
                    if (isNeighbor(pos_a.get(i), pos_b.get(j), digraph)) {
                        res = true;
                        pathEnds.add(pos_b.get(j));
                    }
                }
            }    
            return res;
        } else {
            ArrayList<Integer> p = new ArrayList<Integer>();
            for (int i = 0; i < pathEnds.size(); i++) {
                for (int j = 0; j < pos_b.size(); j++) {
                    if (isNeighbor(pathEnds.get(i), pos_b.get(j), digraph)) {
                        p.add(pos_b.get(j));
                        res = true;
                    }
                }
                
            }
            if (res) {
                pathEnds.clear();
                pathEnds.addAll(p);
            }
        }
        return res;
    }

    private Digraph buildGraph(BoggleBoard board) {
        int nrow = board.rows();
        int ncol = board.cols();
        
        Digraph digraph = new Digraph(nrow * ncol);
        
        for (int x = 0;  x < nrow; x ++) {
            for (int y = 0; y < ncol; y++) {
                if (isInBoard(x + 1, y, nrow, ncol)) {
                    digraph.addEdge(toOneDim(x, y, ncol), toOneDim(x + 1, y, ncol));
                }
                
                if (isInBoard(x - 1, y, nrow, ncol)) {
                    digraph.addEdge(toOneDim(x, y, ncol), toOneDim(x - 1, y, ncol));
                }
                
                if (isInBoard(x , y + 1, nrow, ncol)) {
                    digraph.addEdge(toOneDim(x, y, ncol), toOneDim(x , y + 1, ncol));
                }
                
                if (isInBoard(x , y - 1, nrow, ncol)) {
                    digraph.addEdge(toOneDim(x, y, ncol), toOneDim(x , y - 1, ncol));
                }
                
                if (isInBoard(x - 1, y - 1, nrow, ncol)) {
                    digraph.addEdge(toOneDim(x, y, ncol), toOneDim(x-1 , y - 1, ncol));
                }
                
                if (isInBoard(x + 1, y - 1, nrow, ncol)) {
                    digraph.addEdge(toOneDim(x, y, ncol), toOneDim(x + 1 , y - 1, ncol));
                }
                
                if (isInBoard(x + 1, y + 1, nrow, ncol)) {
                    digraph.addEdge(toOneDim(x, y, ncol), toOneDim(x + 1 , y + 1, ncol));
                }
                
                
                if (isInBoard(x - 1, y + 1, nrow, ncol)) {
                    digraph.addEdge(toOneDim(x, y, ncol), toOneDim(x - 1 , y + 1, ncol));
                }
                
            }
        }
        
        return digraph;
    }

    
    //TODO:
    // Will be improved later
    // Check whether the two node n and m are neighbor or not in digraph
    private boolean isNeighbor(int n, int m, Digraph digraph) {
        for(int w:digraph.adj(n)) {
            if (w == m) {
                return true;
            }
        }
        return false;
    }
    
    
    private boolean containsCharMoreThanOnes(String word) {
        Set<Character> distinct = new HashSet<Character>();
        
        for (int i = 0; i < word.length(); i++) {
            distinct.add(word.charAt(i));
        }
        
        return (distinct.size() != word.length());
        
    }
    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
  
        
        Set<String> res = new HashSet<String>();
        HashMap<Character, ArrayList<Integer> > positionOfCharacter = cmptPositionOfCharacter(board);
        Digraph digraph = buildGraph(board);
        Queue<String> results = new LinkedList<String>();
        StringBuilder prefix = new StringBuilder("");
        ArrayList<Integer> pathEnds = new ArrayList<Integer>();
        collect(root, prefix,results,positionOfCharacter,digraph,pathEnds );
       
        ArrayList<String> sortedRes = new ArrayList<String>(results);
        Collections.sort(sortedRes);
        return sortedRes;
    }
 
    private HashMap<Character, ArrayList<Integer>> cmptPositionOfCharacter(
            BoggleBoard board) {
        HashMap<Character, ArrayList<Integer> > positionOfCharacter = new HashMap<Character, ArrayList<Integer>>();
        int nrow = board.rows();
        int ncol = board.cols();
        
        for (int x = 0; x < nrow; x++) {
            for (int y = 0; y < ncol; y++) {
                char c = board.getLetter(x, y);
                if (positionOfCharacter.containsKey(c)) {
                    positionOfCharacter.get(c).add(y * nrow + x);
                } else {
                    ArrayList<Integer> pos = new ArrayList<Integer>();
                    pos.add(y * nrow + x);
                    positionOfCharacter.put(c, pos);
                }
            }
        }
        
        return positionOfCharacter;
        
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int wordLength = word.length();
        
        if (wordLength ==3 || wordLength == 4) {
            return 1;
        }
        
        if (wordLength == 5) {
            return 2;
        }
        
        if (wordLength == 6) {
            return 3;
        }
        
        if (wordLength == 7) {
            return 5;
        }
        
        if (wordLength >= 8 ) {
            return 11;
        }
        return 0;
    }
    

}
