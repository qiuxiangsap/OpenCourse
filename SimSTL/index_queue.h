/********************************************************************************************/
/* The file implementing the Index Min Queue according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 13th, 2014 
/*********************************************************************************************/

#ifndef __INDEX_QUEUE_H
#define __INDEX_QUEUE_H

namespace Algorithm {

	template <typename Key>
	struct IndexMinQ {
	private:

		void exch(const int &f, const int & t) {
			int c = pq[f];
			pq[f] = pq[t];
			pq[t] = c;

			qp[pq[f]] = f;
			qp[pq[t]] = t;
		}

		void swim(int pos) {
			while (pos > 1 && keys[pq[pos]] < keys[pq[pos / 2]]) {
				exch(pos, pos / 2);
				pos /= 2;
			}
		}


		void sink(int pos) {
			while (true) {
				int left = 2 * pos;
				if (left > n) {
					break;
				}
				if (left < n && keys[pq[left]] > keys[pq[left + 1]]) {
					left = left + 1;
				}

				if (keys[pq[left]] < keys[pq[pos]]) {
					exch(left, pos);
					pos = left;
				} else {
					break;
				}
			}
		}

	public:
		IndexMinQ(const int capacity) {
			n = 0;
			this->capacity = capacity;
			pq = new int[capacity + 1];
			qp = new int[capacity + 1];
			keys = new Key[capacity + 1];

			for (int i = 0; i < capacity + 1; i++) {
				qp[i] = -1;
			}
		}

		bool is_empty() {
			return n == 0;
		}

		void insert(int index, Key key) {
			n++;
			pq[n] = index;
			qp[index] = n;
			keys[index] = key;
			swim(n);
		}

		void decrease_key(int index, Key key) {
			keys[index] = key;
			swim(qp[index]);
		}

		// delete the minimum value
		int del_min() {
			int min_idx = pq[1];
			qp[min_idx] = -1;
			exch(1, n--);
			sink(1);
			return min_idx;
		}

		bool contains(const int & index) {
			return qp[index] != -1;
		}

		int *pq;
		int *qp;
		Key * keys;
		int n;
		int capacity;

	};

}
#endif