/*************************************************************************************/
/* The file implementing Bellman Ford according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 13th, 2014 
/*********************************************************************************************/

#ifndef __BELLMAN_FORD_H
#define __BELLMAN_FORD_H

#include "graph.h"
#include <limits>

using namespace std;

namespace Algorithm {
	class Bellman_Ford {
	public:
		double dist_to(int w); // distance to a node w, from source s
		bool has_path_to(int w); // check whether path exist betweeen w and source s

		Bellman_Ford(EdgeWeightedGraph &graph, int s);
		~Bellman_Ford();

	private:
		bool check(int w); // check whether the node passed is valid or not;

		const int s; // source node
		double *distTo;
		DirectedEdge *pathTo;
		int n; // number of nodes in the graph

	};

	Bellman_Ford::Bellman_Ford(EdgeWeightedGraph &graph, int s):s(s) {
		n = graph.v;
		distTo = new double[n];
		pathTo = new DirectedEdge[n];

		for (int i = 0; i < n; i++) {
			distTo[i] = numeric_limits<double>::max();
		}

		distTo[s] = 0.0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				vector<DirectedEdge> * nebors = graph.adj(j);
				if (nebors == NULL) continue;
				for (int k = 0; k < nebors->size(); k++) {
					int f = (*nebors)[k].from;
					int t = (*nebors)[k].to;

					if (distTo[t] > (distTo[f] + (*nebors)[k].weight) ) {
						distTo[t] = (distTo[f] + (*nebors)[k].weight);
					}
				}
			}
		}
	}


	Bellman_Ford::~Bellman_Ford() {
		delete [] distTo;
		delete [] pathTo;
		distTo = NULL;
		pathTo = NULL;
	}

	bool Bellman_Ford::has_path_to(int w) {
		if (!check(w)) throw new invalid_argument("Node should be " + n);
		return distTo[w] != numeric_limits<double>::max();
	}

	double Bellman_Ford::dist_to(int w) {
		if (!check(w)) throw new invalid_argument("Invalid Argument");
		return distTo[w];
	}

	bool Bellman_Ford::check(int w) {
		if (w < 0 || w >= n) {
			throw new invalid_argument("Invalid Argument");
		}
	}

}
#endif