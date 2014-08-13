/********************************************************************************************/
/* The file implementing Union Find according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#include <iostream>

using namespace std;

class UnionFind {
public:
	UnionFind(int n);
	~UnionFind();

	void join(int p, int q);
	bool find(int p, int q);
	int count(); // number of components

private:
	int root(int p);

	int n;
	int *node;
	int *num; // size of of each tree
	int cnt;
};

UnionFind::UnionFind(int n):n(n) {
	node = new int[this->n];
	num = new int[this->n];

	for (int i = 0; i < this->n; i++) {
		node[i] = i;
		num[i] = 1;
	}
	cnt = n;

}

UnionFind::~UnionFind() {
	delete[] node;
	delete[] num;
}

void UnionFind::join(int p, int  q) {
	int p_root = root(p);
	int q_root = root(q);

	if (p_root != q_root) {
		int pr_size = num[p_root];
		int qr_size = num[q_root];

		if ( pr_size < qr_size ) {
			node[p_root] = q_root;
			num[q_root] += pr_size;
		} else {
			node[q_root] = p_root;
			num[p_root] += qr_size;
		}
		cnt--;
	}
}

bool UnionFind::find(int p , int q) {
	return root(p) == root(q);
}

int UnionFind::root(int p) {
	while(node[p] != p) {
		node[p] = node[node[p]];
		p = node[p];
	}
	return p;
}

int UnionFind::count() {
	return cnt;
}

int main(int argc, char *argv[]) {
	UnionFind *u = new UnionFind(10);
	u->join(4,3);
	u->join(3,8);
	u->join(6,5);
	u->join(9,4);
	u->join(2,1);
	
	cout << "Is Connected :" << u->find(0,7) << endl;
	cout << "Is Connected :" << u->find(8,9) << endl;

	u->join(5, 0);
	u->join(7,2);
	u->join(6,1);
	u->join(1,0);

	cout << "Is Connected: " << u->find(0, 7) << endl;
	cout << "Number of counts " << u->count() << endl;

	return 0;
}