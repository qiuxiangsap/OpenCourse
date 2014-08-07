package com.sap.nic.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.omg.CORBA.INTERNAL;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.introcs.In;

public class WordNet {

    // constructor takes the name of the two input files
    public WordNet(String syn, String hyper) {

        In synReader = null;
        In hypReader = null;

        synsets = new ArrayList<String>();
        definition = new ArrayList<String>();
        wordIds = new HashMap<String, Integer>();
        nons = new HashSet<String>();
        
        try {
            synReader = new In(syn);
            hypReader = new In(hyper);

            String line = null;
            int numOfNodes = 0;
            while ((line = synReader.readLine()) != null) {
                String[] elemnts = line.split(",");
                String[] words = elemnts[1].split(" ");
                for (String word : words) {
                    wordIds.put(word, Integer.valueOf(elemnts[0]));
                }

                ArrayList<String> syns = new ArrayList<String>(Arrays.asList(words));
                synsets.add(elemnts[1]);
                definition.add(elemnts[2]);
                nons.addAll(syns);
                numOfNodes++;
            }
            syn_id = numOfNodes;

            line = null;
            this.hypernyms = new Digraph(numOfNodes);

            while ((line = hypReader.readLine()) != null) {
                String[] nodes = line.split(",");
                for (int i = 0; i < nodes.length - 1; i++) {
                    hypernyms.addEdge(Integer.valueOf(nodes[0]),Integer.valueOf(nodes[i + 1]));
                }

            }
            
            DirectedCycle dCycle = new DirectedCycle(hypernyms);
            if( dCycle.hasCycle() ) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns() {
        return nons;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nons.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!wordIds.containsKey(nounA) || !wordIds.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }
        
        int idA = wordIds.get(nounA);
        int idB = wordIds.get(nounB);

        if (idA == idB) {
            return 0;
        }
         
        int[] distToA = shortestPath(idA, hypernyms);
        int[] distToB = shortestPath(idB, hypernyms);
        
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
    
    // a synset (second field of synsets.txt) that is the common ancestor of
    // nounA and nounB
    // in a shortest ancestral path (defined below)
    
    
    public String sap(String nounA, String nounB) {
        if (!wordIds.containsKey(nounA) || !wordIds.containsKey(nounB)) {
            throw new IllegalArgumentException();
        }
        
        int idA = wordIds.get(nounA);
        int idB = wordIds.get(nounB);
        
        if (idA == idB) {
            return nounA;
        }
        
        int[] distToA = shortestPath(idA, hypernyms);
        int[] distToB = shortestPath(idB, hypernyms);
        
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
            return null;
        } else {
            return synsets.get(ancestor);
        }
    }


    private Digraph hypernyms;

    // for unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms100K.txt");
//      System.out.println(wordNet.distance("spike", "exorbitance"));
      System.out.println(wordNet.distance("North", "mutual_inductance"));
      System.out.println(wordNet.sap("North", "mutual_inductance"));
    }

    private ArrayList<String> synsets;
    private int syn_id;
    private ArrayList<String> definition;
    private Set<String> nons;
    private HashMap<String, Integer> wordIds;

}
