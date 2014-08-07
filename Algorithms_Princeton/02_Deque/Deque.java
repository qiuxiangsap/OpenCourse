package com.sap.nic.chapter2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    public static void main(String[] args) {
	Deque<Integer> dque = new Deque<Integer>();
	dque.addFirst(10);
	dque.addFirst(132);
	System.out.println(dque.size());
	
    }
    
    private class Node<Item> {
	Item item;
	Node<Item> next;
	Node<Item> prev;
	public Node(Item item) {
	    this.item = item;
        }
	
    }
    
    private Node<Item> first;
    private Node<Item> last;
    private int count;
    
    /**
     * construct an empty deque
     */
    public Deque() {
	first = null;
	last = null;
	count = 0;
    }
    
    /**
     *  is the deuqe empty
     * @return
     */
    public boolean isEmpty(){
	return count == 0;
    }
    
    /**
     * return the number of items on the deque
     * @return
     */
    public int size(){
	return count;
    }
    
    /**
     * insert the item at the front
     * @param item
     */
    public void addFirst(Item item) {
	if(item == null) {
	    throw new NullPointerException();
	}
	
	Node<Item> current = new Node<Item>(item);
	current.next = first;
	
	if(last == null) {
	    last = current;
	}
	
	if(first != null) {
	    first.prev = current;
	}
	
	first = current;
	count++;
    }
    
    /**
     * insert the item at the end
     * @param item
     */
    public void addLast(Item item) {
	if(item == null) {
	    throw new NullPointerException();
	}
	
	Node<Item> current = new Node<Item>(item);
	if(last != null) {
	    last.next = current;
	    current.prev = last;
	    last = current;
	}else {
	    current.next = last;
	    last = current;
	    if(first == null) {
		first = last;
	    }
	}
	count++;
    }
    
    /**
     * delete and return the item at the front
     * @return
     */
    public Item removeFirst() {
	if(isEmpty()) {
	    throw new NoSuchElementException();
	}
	Item item = first.item;
	first = first.next;
	count--;
	return item;
    }
    
    /**
     * delete and return the item at the end
     * @return
     */
    public Item removeLast() {
	if(isEmpty()) {
	    throw new NoSuchElementException();
	}
	
	Item item = last.item;
	last = last.prev;
	count--;
	return item;
    }
    
    /**
     * return an iterator over items in order from front to end
     */
    @Override
    public Iterator<Item> iterator() {
	return new DequeIterator<Item>(first);
    }
    
    private class DequeIterator<Item> implements Iterator<Item>{
	Node<Item> current ;

	public DequeIterator(Node<Item> first) {
	    this.current = first;
        }

	@Override
        public boolean hasNext() {
	    return current != null;
        }

	@Override
        public Item next() {
	    if(current == null) {
		throw new NoSuchElementException();
	    }
	    Item item = current.item;
	    current = current.next;
	    return item;
        }

	@Override
        public void remove() {
	    throw new UnsupportedOperationException();
        }
	
    }

}
