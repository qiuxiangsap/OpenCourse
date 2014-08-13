/********************************************************************************************/
/* The file implementing Queue algorithm according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#ifndef __QUEUE_H
#define __QUEUE_H

#include <iostream>

namespace Algorithm {
	template <class T>
	class Queue 
	{
	public:
		Queue();
		~Queue();
		void enqueue(const T& elem);
		T dequeue();
	private:
		struct node {
			T elem;
			node * prev, next;
		};

		node *head, tail;
		int sz;
	};

	template <typename T>
	Queue<T>::Queue() {
		head = tail = null;
		sz = 0;
	}

	template <typename T>
	Queue<T>::~Queue() {
	}

}


#endif