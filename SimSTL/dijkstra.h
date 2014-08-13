#ifndef __DIJKSTRA_H
#define __DIJKSTRA_H

#include <vector>
#include <map>
#include <limits>
#include <fstream>
#include <sstream>
#include <string>
#include <iostream>
#include "index_queue.h"
#include "graph.h"

using namespace std;

namespace Algorithm {

	class Dijkstra {
		double * distTo;
		int n; // number of nodes;
		DirectedEdge *pathTo;

	public:
		Dijkstra( EdgeWeightedGraph &graph, const int& s) {
			n = graph.v;
			distTo = new double[n];
			pathTo = new DirectedEdge[n];



			for (int i = 0; i < n; i++) {
				distTo[i] = (numeric_limits<double>::max )();
			}

			distTo[s] = 0.0;

			IndexMinQ<double> iq(n);
			iq.insert(s, 0.0);

			while (!iq.is_empty()) {
				int w = iq.del_min();
				const vector<DirectedEdge> *nebors = graph.adj(w); 
				if (nebors == NULL) {
					continue;
				}
				for (int i = 0; i < nebors->size(); i++)  {
					const DirectedEdge& edge = (*nebors)[i];
					if (distTo[edge.to] > (distTo[edge.from] + edge.weight)) {
						distTo[edge.to] = distTo[edge.from] + edge.weight;
						pathTo[edge.to] = edge;
						if (iq.contains(edge.to)) {
							iq.decrease_key(edge.to, distTo[edge.to]);
						} else {
							iq.insert(edge.to, distTo[edge.to]);
						}
					}
				}
			}
		}

		double dist_to (int w) {
			return distTo[w];
		}
	};

}

#endif