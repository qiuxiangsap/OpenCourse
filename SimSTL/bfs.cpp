/********************************************************************************************/
/* The file implementing the test client code for BFS 
/* 
/* Author: Zhongcun Wang
/* Date  : Augugst 13th, 2014 
/*********************************************************************************************/

#include "bfs.h"
#include "graph.h"
#include <iostream>
#include <cassert>
#include <string>
#include <iterator>
#include <algorithm>
#include <fstream>

using namespace std;
using namespace Algorithm;

int main(int argc, char ** argv) {
	UDGraph ud(string("undirected_graph.txt"));
	BFS bfs(ud, 0);

	cout << "Shortest Path to 4 is :" << bfs.dist_to(4) << endl;
	//cout << bfs.dist_to(10) << endl;
	vector<int> *path = bfs.path_to(4);
	
	copy(path->begin(), path->end(), ostream_iterator<int>(cout, "\n"));
	return 0;
}