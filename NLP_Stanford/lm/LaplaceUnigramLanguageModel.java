package com.sap.nic.nlp.lm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaplaceUnigramLanguageModel implements LanguageModel {

    /** Initialize your data structures in the constructor. */
    public LaplaceUnigramLanguageModel(HolbrookCorpus corpus) {
	wordProb = new HashMap<String, Double>();
	train(corpus);
    }

    /**
     * Takes a corpus and trains your language model. Compute any counts or
     * other corpus statistics in this function.
     */
    public void train(HolbrookCorpus corpus) {
	for (Sentence sentence : corpus.getData()) { // iterate over sentences
	    for (Datum datum : sentence) { // iterate over words
		String word = datum.getWord(); // get the actual word
		increment(word, 1); // increase the ocurrence of the specific
				    // word
	    }
	}

	// compute the probability of each word with adding one smoothing
	double nVoca = wordProb.size();
	int total = 0;

	for (String word : wordProb.keySet()) {
	    total += wordProb.get(word);
	}

	for (String word : wordProb.keySet()) {
	    setValue(word, (wordProb.get(word) + 1) / (total + nVoca + 1));
	}

	probNonExst = 1.0 / (nVoca + total + 1);

    }

    /**
     * Takes a list of strings as argument and returns the log-probability of
     * the sentence using your language model. Use whatever data you computed in
     * train() here.
     */
    public double score(List<String> sentence) {
	double scr = 0.0;
	for(String word: sentence) {
	    if(isInDict(word)) {
		scr += Math.log(wordProb.get(word));
	    } else {
		scr += Math.log(probNonExst);
	    }
	}
	return scr;
    }

    private boolean isInDict(String word) {
	return wordProb.containsKey(word);
    }
    /**
     * Increase the frequency of the specific word
     * 
     * @param word
     * @param num
     */
    private void increment(String word, double num) {
	if (wordProb.containsKey(word)) {
	    wordProb.put(word, wordProb.get(word) + num);
	} else {
	    wordProb.put(word, num);
	}
    }

    /**
     * Set value for word
     * 
     * @param word
     * @param value
     */
    private void setValue(String word, double value) {
	wordProb.put(word, value);
    }

    private Map<String, Double> wordProb;
    private double probNonExst;
}
