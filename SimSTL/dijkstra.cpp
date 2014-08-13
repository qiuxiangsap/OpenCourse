/********************************************************************************************/
/* This is the test client for Dijkstra Algorithm
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#include "dijkstra.h"
#include "graph.h"
#include <string>

using namespace std;
using namespace Algorithm;

int main(int argc, char ** argv)  {
	string file_name = string("edge_weighted.txt");
	EdgeWeightedGraph edge(file_name);
	Dijkstra *dij = new Dijkstra(edge, 0);

	cout << "Distance To 2" << dij->dist_to(2) << endl;
	return 0;
}