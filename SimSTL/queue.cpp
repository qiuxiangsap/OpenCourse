/********************************************************************************************/
/* The file implementing Queue algorithm according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#ifndef __QUEUE_H
#define __QUEUE_H

#include <iostream>
using namespace std;

namespace Algorithm {
	template <class T>
	class Queue 
	{
	public:
		Queue();
		~Queue();
		void enqueue(T elem);
		T dequeue();
		bool is_empty();
	private:
		struct node {
			T elem;
			node *next;
			node(T &elem):elem(elem), next(NULL) {

			}
		};

		node *head, *tail;
		int sz;
	};

	template <class T>
	void Queue<T>::enqueue(T elem) {
		node *new_node = new node(elem);
		if (tail == NULL) {
			head = tail = new_node;
		} else {
			tail->next = new_node;
			tail = new_node;
		}
		sz++;
	}

	template <typename T>
	Queue<T>::Queue() {
		head = tail = NULL;
		sz = 0;
	}

	template <typename T>
	T Queue<T>::dequeue() {
		if (head == NULL) throw new runtime_error("Empty Queue");
		T v = head->elem;
		head = head->next;
		if (head == NULL) tail = NULL;
		sz--;
		return v;
	}

	template <typename T>
	Queue<T>::~Queue() {
		while (head != tail) {
			node *old = head;
			head = head->next;
			delete old;
		}
	}

	template <typename T>
	bool Queue<T>::is_empty() {
		return sz == 0;
	}

}

int main(int argc, char ** argv) {
	Algorithm::Queue<int> q;
	q.enqueue(10);
	q.enqueue(1);
	q.enqueue(8);

	cout << "First Element is " << q.dequeue() << endl;
	return 0;
}

#endif