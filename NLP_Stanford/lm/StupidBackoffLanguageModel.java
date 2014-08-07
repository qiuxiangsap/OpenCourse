package com.sap.nic.nlp.lm;

import java.util.HashMap;
import java.util.List;

public class StupidBackoffLanguageModel implements LanguageModel {

    /** Initialize your data structures in the constructor. */
    public StupidBackoffLanguageModel(HolbrookCorpus corpus) {
        unigram = new HashMap<String, Double>();
        begin = new HashMap<String, Double>();
        bigram = new HashMap<String, HashMap<String, Double>>();
                
        train(corpus);
    }

    /**
     * Takes a corpus and trains your language model. Compute any counts or
     * other corpus statistics in this function.
     */
    public void train(HolbrookCorpus corpus) {
        List<Sentence> sentences = corpus.getData();
        int numOfWords = 0;
        int numOfSentences = 0;

        for (Sentence sentence : sentences) {
            numOfSentences++;
            boolean first = true;
            String prev = null;
            for (Datum data : sentence) {
                String word = data.getWord();
                numOfWords++;

                incrementUnigram(word, 1.0);
                if (first) {
                    incrementBegin(word, 1.0);
                    prev = word;
                    first = false;
                } else {
                    incrementBigram(prev, word, 1);
                    prev = word;
                }

            }
        }

        // compute unigram probability with laplace smoothing
        int vocabularySize = unigram.keySet().size();
        for (String word : unigram.keySet()) {
            double probability = (unigram.get(word) + 1)
                    / (vocabularySize + 1 + numOfWords);
            unigram.put(word, probability);
        }

        double unknownProb = 1.0 / (vocabularySize + 1.0 + numOfWords);
        unigram.put(unknowToken, unknownProb);
        
        //compute begin probability with laplace smoothing
        for (String word:begin.keySet()) {
            double probability = (begin.get(word) + 1.0) / (vocabularySize + 1 + numOfSentences);
            begin.put(word, probability);
        }
        
        double unkonwnBeginProb =  1 / (vocabularySize + 1 + numOfSentences);
        begin.put(unknowToken, unkonwnBeginProb);
        
    }

    private void incrementBigram(String prev, String next, double value) {
        if (bigram.containsKey(prev) && bigram.get(prev).containsKey(next)) {
            bigram.get(prev).put(next, bigram.get(prev).get(next) + value);
        } else if (!bigram.containsKey(prev)) {
            HashMap<String, Double> w_count = new HashMap<String, Double>();
            w_count.put(next, value);
            bigram.put(prev, w_count);
        } else {
            bigram.get(prev).put(next, value);
        }

    }

    private void incrementUnigram(String word, double freq) {
        if (unigram.containsKey(word)) {
            unigram.put(word, unigram.get(word) + freq);
        } else {
            unigram.put(word, freq);
        }
    }

    private void incrementBegin(String word, double freq) {
        if (begin.containsKey(word)) {
            begin.put(word, begin.get(word) + freq);
        } else {
            begin.put(word, freq);
        }
    }


    private double getStupidBackOffValue(String prev, String next) {
        if (bigram.containsKey(prev) && bigram.get(prev).containsKey(next)) {
            return bigram.get(prev).get(next);
        } else {
            if (unigram.keySet().contains(next)) {
                return discountFactor * unigram.get(next);
            } else {
                return discountFactor * unigram.get(unknowToken);
            }
                 
        }
    }

    /**
     * Takes a list of strings as argument and returns the log-probability of
     * the sentence using your language model. Use whatever data you computed in
     * train() here.
     */
    public double score(List<String> sentence) {
        
        if (sentence.size() < 1) {
            throw new IllegalArgumentException();
        }
        
        double logScore = Math.log(begin.get(sentence.get(0)));
        
        for (int i = 1; i < sentence.size(); i++){
            logScore += Math.log(getStupidBackOffValue(sentence.get(i - 1), sentence.get(i)));
        }
                
        return logScore;
    }

    private HashMap<String, Double> unigram;
    private HashMap<String, HashMap<String, Double>> bigram;
    private HashMap<String, Double> begin;
    private double discountFactor = 0.4;
    private final static String unknowToken = "@_UNTOKEN@";

}
