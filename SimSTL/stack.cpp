/********************************************************************************************/
/* The file implementing Stack according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#ifndef __STACK_H
#define __STACK_H
#include <iostream>

using namespace std;

namespace Algorithm {


	template <class T>
	class Stack {

	public:
		Stack();
		~Stack();

		void push(const T& elem);
		T pop();
		bool empty();
		int size();

	private:
		struct Node {
			T elem;
			Node *next;
			Node(){}
		};


		Node *head;
		int sz;
	};

	template <class T>
	Stack<T>::Stack() {
		head = NULL;
		sz = 0;
	}

	template <class T>
	Stack<T>::~Stack() {
		for (;head != NULL;) {
			Node *s = head;
			head = head->next;
			delete s;
		}
		head = NULL;
	}

	template <class T>
	bool Stack<T>::empty() {
		return sz == 0;
	}

	template <class T>
	void Stack<T>::push(const T &elem) {
		Node *node = new Node();
		node->elem = elem;
		node->next = head;
		head = node; 
		sz++;
	}

	template <class T>
	T Stack<T>::pop() {
		if (this->empty()) {
			throw "Empty Stack";
		}

		T el = head->elem;
		head = head->next;
		sz--;
		return el;


	}

	template <class T>
	int Stack<T>::size() {
		return sz;
	}

}

#endif

int main(int argc, char*argv[])  {
	Algorithm::Stack<int> *s = new Algorithm::Stack<int>();
	s->push(10);
	s->push(9);
	s->push(8);
	s->push(7);

	cout << "Size of S: " << s->size() << endl;
	cout << "Head of S:" << s->pop() << endl;

}