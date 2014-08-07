package com.sap.nic.graph;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

public class Outcast {

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int max = Integer.MIN_VALUE;
        int max_id = -1;
        
        for(int i = 0; i < nouns.length; i++) {
            int dst = distance(i, nouns);
            if (dst < max) {
                max = dst;
                max_id = i;
            }
        }
        
        return nouns[max_id];
    }
    
    private int distance(int i, String[] nouns) {
        int dist_sum = 0;
        for (int j = 0; j < nouns.length && j != i; j++) {
            dist_sum += wordNet.distance(nouns[i], nouns[j]);
        }
        return dist_sum;
    }
  
    private WordNet wordNet;
    
    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
