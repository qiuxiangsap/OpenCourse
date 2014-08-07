package com.sap.nic.nlp.lm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LaplaceBigramLanguageModel implements LanguageModel {
  
  /** Initialize your data structures in the constructor. */
  public LaplaceBigramLanguageModel(HolbrookCorpus corpus) {
    unigram = new HashMap<String, Integer>();
    beginProb = new HashMap<String, Double>();
    bigram = new HashMap<String, HashMap<String, Double>>();
    train(corpus); 
  }
  
  /** Takes a corpus and trains your language model. 
    * Compute any counts or other corpus statistics in this function.
    */
  public void train(HolbrookCorpus corpus) { 
	int numOfSentence = 0;
	
	for (Sentence sentence : corpus.getData()) {
	    numOfSentence++;
	    
	    int count = 0;
	    String prev = null;

	    for (Datum datum : sentence) {
		String word = datum.getWord();
		if (unigram.containsKey(word)) {
		    unigram.put(word, unigram.get(word) + 1);
		} else {
		    unigram.put(word, 1);
		}
	
		
		if (count == 0) {
		    prev = word;
		    beginProb.put(word, 1.0);
		    count++;
		} else {
		    incrementBigram(prev, word, 1.0);
		    prev = word;
		}
	    }
	}
	
	
	List<String> vocabulary = new ArrayList<String>(unigram.keySet());
	vocabulary.add("_NO_EXST");
	
	for (String w1:vocabulary) {
	    double w_total = 0.0;
	    if (unigram.containsKey(w1)) {
	        w_total = unigram.get(w1); 
	    }
	    
	    	
	    for (String w2:vocabulary) {
	        double next = getBigram(w1, w2);
	        double prob = (next + 1.0) / (w_total + vocabulary.size());
	        putBigram(w1, w2, prob);
	    }
	}
	
	
	for (String word:vocabulary) {
	    double num = 0.0;
	    if (beginProb.containsKey(word)) {
	        num = beginProb.get(word);
	    }
	    double prob = (num + 1.0) / ( vocabulary.size() + numOfSentence );
	    beginProb.put(word, prob);
	}

  }

  private void incrementBigram(String key1, String key2, double value) {
      if (bigram.containsKey(key1) && bigram.get(key1).containsKey(key2)) {
          bigram.get(key1).put(key2, bigram.get(key1).get(key2) + value);
      } else if (!bigram.containsKey(key1)) {
          HashMap<String, Double> number = new HashMap<String, Double>();
          number.put(key2, value);
          bigram.put(key1, number);
      } else {
          bigram.get(key1).put(key2, value);
      }

  }
  
  private void putBigram(String key1, String key2, double value) {
      if (bigram.containsKey(key1)) {
          bigram.get(key1).put(key2, value);
      } else {
          HashMap<String, Double> probability = new HashMap<String, Double>();
          probability.put(key2, value);
          bigram.put(key1, probability);
      }
  }
  
  private double getBigram(String key1, String key2) {
      if (bigram.containsKey(key1) && bigram.get(key1).containsKey(key2)) {
          return bigram.get(key1).get(key2);
      }
      
      return 0.0;
  }

  /** Takes a list of strings as argument and returns the log-probability of the 
    * sentence using your language model. Use whatever data you computed in train() here.
    */
  public double score(List<String> sentence) {
    if (sentence.size() < 1) {
        throw new IllegalArgumentException();
    }
    
    double beginProb  = getBeginProb(sentence.get(0));

    double logScore = Math.log(beginProb);
    
    for (int i = 1; i  < sentence.size(); i++) {
        logScore += Math.log(getConditionProb(sentence.get(i - 1), sentence.get(i)));
    }
    return logScore;
  }
 
  
  /**
   * Get the probability for a word to be the start of an sentence
   * 
   * @param word
   * @return
   */
  private double getBeginProb(String word) {
      if (beginProb.containsKey(word)) {
          return beginProb.get(word);
      } else {
          return beginProb.get("_NO_EXST");
      }
  }
  
  /**
   * Get the probability of the word condition on condWord
   * 
   * @param condWord
   * @param word
   * @return
   */
  private double getConditionProb(String condWord, String word) {
      String prev = condWord;
      String next = word;
      
      if (!unigram.containsKey(condWord)) {
          prev = "_NO_EXST";
      } 
      
      if (!unigram.containsKey(word)) {
          next = "_NO_EXST";
      }
      
      return getBigram(prev, next);
  }
  
  
  private Map<String, Double> beginProb; // probability for a specific word as the beginning of an sentence
  private Map<String, Integer> unigram;
  private HashMap<String, HashMap<String, Double>> bigram;

}
