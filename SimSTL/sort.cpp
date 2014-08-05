#include <iostream>
#include <algorithm>
#include <iterator>
#include <omp.h>
using namespace std;

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

void bubble_sort(int *arr, int len) {
}

void select_sort(int *arr, int len) {
}

void shell_sort(int *arr, int len) {
}

void qsort(int *arr, int len) {
}

int main(int argc, char*argv[]) 
{
	//int arr[] = {10, 8, 4, 2};
	//copy(arr, arr + sizeof(arr) / sizeof(arr[0]), ostream_iterator<int>(cout, " "));
	//insert_sort<int>(arr, sizeof(arr) / sizeof(arr[0]));
	//copy(arr, arr + sizeof(arr) / sizeof(arr[0]), ostream_iterator<int>(cout, " "));
	int id;
	#pragma omp parallel private(id) 
	{
		id = omp_get_thread_num();
		cout << id << endl;
	}
	return 0;
}