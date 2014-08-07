/**
 * 
 */
package com.sap.nic.chapter2;

import java.util.Iterator;

import edu.princeton.cs.introcs.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
	capacity = 1;
	size = 0;
	items = (Item[]) new Object[capacity];
    }

    /**
     * is the queue empty
     * 
     * @return
     */
    public boolean isEmpty() {
	return size == 0;
    }

    /**
     * return the number of items on the queue
     * 
     * @return
     */
    public int size() {
	return size;
    }

    /**
     * add the item
     * 
     * @param item
     */
    public void enqueue(Item item) {
	if (capacity == size) {
	    grow();
	}

	items[size++] = item;
    }

    private void grow() {
	capacity *= 2;
	Item[] dup = (Item[]) new Object[capacity];

	for (int i = 0; i < size; i++) {
	    dup[i] = items[i];
	}

	items = dup;
    }

    /**
     * Get an random postion whose element is not null
     * 
     * @return
     */
    private int randPos() {
	while (true) {
	    int pos = StdRandom.uniform(capacity);
	    if (items[pos] != null) {
		return pos;
	    }
	}
    }

    /**
     * delete and return an random item
     * 
     * @return
     */
    public Item dequeue() {
	int pos = randPos();
	Item item = items[pos];
	items[pos] = null;
	size--;
	if (size == capacity / 4) {
	    shrink(capacity / 2);
	}
	return item;
    }

    private void shrink(int cap) {
	Item[] it = (Item[]) new Object[cap];

	int idx = 0;

	for (int i = 0; i < capacity; i++) {
	    if (items[i] != null) {
		it[idx++] = items[i];
	    }
	}

	items = it;
    }

    /**
     * return (but not delete ) a random item
     * 
     * @return
     */
    public Item sample() {
	return items[randPos()];
    }

    public static void main(String[] args) {
	RandomizedQueue<Integer> rQueue = new RandomizedQueue<Integer>();

	rQueue.enqueue(10);
	rQueue.enqueue(100);
	
	Iterator<Integer> itr = rQueue.iterator();
	
	while(itr.hasNext()) {
	    System.out.println(itr.next());
	}
    }

    /**
     * return an independent iterator over item in random order
     */
    @Override
    public Iterator<Item> iterator() {
	return new RandQueueIterator<Item>(this);
    }

    private class RandQueueIterator<Item> implements Iterator<Item> {

	private RandomizedQueue<Item> item;

	public RandQueueIterator(RandomizedQueue<Item> item) {
	    this.item = item;
	}

	@Override
	public boolean hasNext() {
	    return !item.isEmpty();
	}

	@Override
	public Item next() {
	    Item it = item.dequeue();
	    return it;
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException();
	}

    }

    private Item[] items;
    private int size;
    private int capacity;

}
