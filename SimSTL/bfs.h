/*************************************************************************************/
/* The file implementing BFS according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 13th, 2014 
/*********************************************************************************************/

#ifndef __BFS_H
#define __BFS_H

#include "graph.h"
#include <queue>
#include <limits>
#include <stack>
#include <stdexcept>

namespace Algorithm {

	class BFS {
	private:
		int *distTo;
		int n;
		bool *marked;
		int *pathTo;

		bool check(int w);

	public:
		BFS(UDGraph &graph, int s);

		~BFS();

		bool has_path_to(int w);
		int dist_to(int w);
		vector<int> * path_to(int w);
	};

	BFS::~BFS()  {
		delete [] distTo;
		delete [] pathTo;
		delete [] marked;
	}
	BFS::BFS(UDGraph &graph, int s){
		n = graph.v();
		distTo = new int[n];
		pathTo = new int[n];
		marked = new bool[n];

		for (int i = 0; i < n; i++) {
			marked[i] = false;
		}

		for (int i = 0; i < n; i++) {
			distTo[i] = numeric_limits<int>::max();
		}

		queue<int> q;

		q.push(s);
		marked[s] = true;
		pathTo[s] = s;
		distTo[s] = 0;

		while (!q.empty()) {
			int w = q.front();
			q.pop();
			vector<int> *nebors = graph.adj(w);
			if (nebors == NULL) continue;
			for (vector<int>::iterator itr = nebors->begin(); itr != nebors->end(); ++itr) {
				if (!marked[*itr]) {
					marked[*itr] = true;
					pathTo[*itr] = w;
					distTo[*itr] = distTo[w] + 1;
					q.push(*itr);
				}
			}
		}
		
	}

	bool BFS::has_path_to(int w) {
		if (!check(w)) {
			throw new invalid_argument("Invalid Argument!");
		}
		return distTo[w] != numeric_limits<int>::max();
	}

	int BFS::dist_to(int w) {
		if (!check(w)) {
			throw new invalid_argument("Invalid Argument!");
		}

		return distTo[w];
	}

	bool BFS::check(int w) {
		if (w < 0 || w > n) {
			return false;
		}

		return true;
	}

	vector<int>*  BFS::path_to(int w) {
		if (!check(w) ){
			throw new invalid_argument("Invalid Argument");
		}

		stack<int> path;
		path.push(w);
		while (pathTo[w] != w) {
			path.push(pathTo[w]);
			w = pathTo[w];
		}
		vector<int> *res = new vector<int>();
		while (!path.empty()) {
			res->push_back(path.top());
			path.pop();
		}
		
		return res;
	}
}

#endif 