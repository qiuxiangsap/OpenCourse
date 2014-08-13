/********************************************************************************************/
/* The file implementing Sort algorithms according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#ifndef __SORT_H
#define __SORT_H

#include <iostream>
#include <algorithm>
#include <iterator>
#include <omp.h>

using namespace std;


namespace Algorithm {
	template <class T>
	void exch(T * arr, const T& i, const T& j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	template <class T>
	void insert_sort(T *arr, int len) {
		for (int i = 0; i < len; i++) {
			for ( int j = i; j > 0; j--) {
				if ( arr[j] < arr[j - 1] ) {
					exch(arr, j, j - 1);
				}
			}
		}
	}

	void merge(int *arr, int low1, int high1, int low2, int high2) {
		int len = high1 - low1 + 1 + high2 - low2 + 1;
		int *u_sorted = new int[len];

		int i = low1;
		int j = low2;
		int num = 0;
		while (i <= high1 && j <= high2 ) {
			if (arr[i] < arr[j]) {
				u_sorted[num++] = arr[i];
				i++;
			}  else {
				u_sorted[num++] = arr[j];
				j++;
			}
		}

		while (i <=high1) {
			u_sorted[num++] = arr[i++];
		}
		while (j <= high2 ) {
			u_sorted[num++] = arr[j++];
		}

		for (int k = low1; k <= high1; k++) {
			arr[k] = u_sorted[k - low1];
		}
		for (int k = low2; k <= high2; k++) {
			arr[k] = u_sorted[high1 - low1 + k - low2 + 1];
		}
		delete [] u_sorted;
	}
	
	void _merge_sort(int *arr, int low, int high) {
		if (low >= high) return;
		int mid = (low + high) / 2;
		_merge_sort(arr, low, mid);
		_merge_sort(arr, mid + 1, high);
		merge(arr, low, mid, mid + 1, high);

	}

	void merge_sort(int *arr, int len) {
		_merge_sort(arr, 0, len - 1);
	}

	void _qsort(int *arr, int low, int high) {
		if (low >= high) {
			return;
		}
		int key = arr[low];
		int first = low;
		int last = high;

		while (first < last) {
			while (first < last && arr[last] >= key) last--;
			arr[first] = arr[last];
			while (first < last && arr[first] <= key) first++;
			arr[last] = arr[first];
		}
		arr[first] = key;
		_qsort(arr, low, first - 1);
		_qsort(arr, first+1, high);
	}

	void q_sort(int *arr, int len) {
		_qsort(arr, 0, len - 1);
	}
}

int main(int argc, char*argv[]) 
{
	int arr[] = {10, 8, 4, 2};
	copy(arr, arr + sizeof(arr) / sizeof(arr[0]), ostream_iterator<int>(cout, " "));
	//Algorithm::q_sort(arr, sizeof(arr) / sizeof(arr[0]));
	Algorithm::merge_sort(arr, sizeof(arr) / sizeof(arr[0]));
	cout << endl;
	copy(arr, arr + sizeof(arr) / sizeof(arr[0]), ostream_iterator<int>(cout, " "));


	return 0;
}

#endif
