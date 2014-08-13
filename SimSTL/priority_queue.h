/*************************************************************************************/
/* The file implementing Priority Queue according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 13th, 2014 
/*********************************************************************************************/

#ifndef __PRIORITY_QUEUE_H
#define __PRIORITY_QUEUE_H

namespace Algorithm {

	template <typename T>
	class Priority_Queue {
	public:
		Priority_Queue(int capacity);
		~Priority_Queue();

		void insert(T  elem);
		T del_min();
		bool is_empty() { return n == 0;}
		int size() { return n; }

	private:
		void swim(int pos);
		void exch(int f, int t);
		void sink(int pos);
		void resize(int num);

		T * elems;
		int capacity;
		int n; // number of elements in current priority queue
	};

	template <typename T>
	Priority_Queue<T>::Priority_Queue(int capacity):capacity(capacity) {
		elems = new T[capacity + 1];
		n = 0;
	}

	template <typename T>
	Priority_Queue<T>::~Priority_Queue() {
		delete [] elems;
	}

	template <typename T>
	void Priority_Queue<T>::insert(T elem) {
		n++;

		if (n > capacity) {
			resize( 2 * capacity);
			capacity *= 2;
		}

		elems[n] = elem;
		swim(n);
	}

	template <typename T>
	void Priority_Queue<T>::swim(int pos) {
		while (pos > 1 && elems[pos] < elems[pos / 2]) {
			exch(pos, pos / 2);
			pos /= 2;
		}
	}

	template <typename T>
	void Priority_Queue<T>::exch(int f, int t) {
		T c = elems[f];
		elems[f] = elems[t];
		elems[t] = c;
	}

	template <typename T>
	T Priority_Queue<T>::del_min() {
		if (is_empty()) throw "Priority Queue Empty !";
		T min_e = elems[1];
		exch(1, n--);
		sink(1);
		return min_e;
	}

	template <typename T>
	void Priority_Queue<T>::sink(int pos) {
		while (true) {
			int left = pos * 2;
			if (left > n) {
				break;
			}
			if ((left + 1) <= n && elems[left] > elems[left + 1] ) {
				left = left + 1;
			}

			if (elems[left] < elems[pos]) {
				exch(left, pos);
				pos = left;
			} else {
				break;
			}
		}
	}

	template <typename T>
	void Priority_Queue<T>::resize(int num) {
		T * another = new T[num];

		for (int i = 1; i < capacity; i++) {
			another[i] = elems[i];
		}
		delete [] elems;
		elems = another;
	}

}

#endif