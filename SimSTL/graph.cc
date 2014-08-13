/********************************************************************************************/
/* The file implementing test client for Graph
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#include "graph.h"
#include <string>
#include <iostream>

using namespace std;

int main(int argc, char **argv) {
	//Algorithm::EdgeWeightedDigraph *edge = new Algorithm::EdgeWeightedDigraph(4, 4);
	//edge->add_edge(DirectedEdge(1,2, 1.0));
	Algorithm::IndexPQueue<int> *iq = new Algorithm::IndexPQueue<int>(11);
	//string strings[] = {"it", "was", "the", "best", "of", "times", "it", "was", "the", "worst"};
	int s[] = {100, 200, 300, 400};
	for (int i = 0; i < 4; i++) {
		iq->insert(i, s[i]);
	}
	cout << "Min" << iq->min_index() << endl;

	return 0;
}