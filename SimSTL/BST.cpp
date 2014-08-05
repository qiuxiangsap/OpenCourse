#include <iostream>
#include <algorithm>
#include <iterator>
#include "BST.h"

using namespace std;

int main(int argc, char** argv) {
	Algorithm::BST<int, int> *bst = new Algorithm::BST<int, int>();
	for (int i = 0; i < 10; i++) {
		bst->put(i,i);
	}
	
	cout << "Size: " << bst->size() << endl;
	std::queue<int> res = bst->level_order();
	int num = res.size();
	for (int i = 0; i < num; i++) {
		cout << res.front() << endl;
		res.pop();
	}

	return 0;
}

