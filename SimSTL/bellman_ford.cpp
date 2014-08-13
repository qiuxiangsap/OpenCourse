#include "bellman_ford.h"
#include <iostream>
#include <cassert>

using namespace Algorithm;
using namespace std;

int main(int argc, char ** argv) {
	EdgeWeightedGraph graph("edge_weighted.txt");
	Bellman_Ford *bf = new Bellman_Ford(graph, 0);

	cout << "Distance to 2 is :" << bf->dist_to(2) << endl;
	assert(bf->dist_to(2) == 14);

	return 0;
}