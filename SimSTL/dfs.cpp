#include "dfs.h"
#include <iostream>
#include <cassert>

using namespace std;
using namespace Algorithm;

int main(int argc, char ** argv) {
	UDGraph ud(string("undirected_graph.txt"));

	DepthFirstSearch dfs(ud, 0);
	cout << dfs.count() << endl;
	assert(dfs.count() == 5);

	return 0;
} 
