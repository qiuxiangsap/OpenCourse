package com.sap.nic.chapter2;

import edu.princeton.cs.introcs.StdIn;


public class Subset {

    public void getSubset(int k) {
        for(int i = 0; i < k; i++) {
            
        }
    }
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        for(int i = 0 ; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }

}
