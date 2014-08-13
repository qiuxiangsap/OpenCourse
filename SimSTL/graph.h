#ifndef __GRAPH_H
#define __GRAPH_H

#include <vector>
#include <map>
#include <fstream>
#include <iostream>
#include <stdexcept>

using namespace std;

namespace Algorithm{

	struct DirectedEdge{
		int from;
		int to;
		double weight;

		DirectedEdge(const int from, const int to, const double weight): from(from), to(to), weight(weight){}
		DirectedEdge(){}

		friend ostream & operator <<(ostream &os, const DirectedEdge &edge) {
			os << "(" << edge.from << " -> " << edge.to << "\n";
			return os;
		}
	};

	struct EdgeWeightedGraph {
		int v;
		map<int, vector<DirectedEdge>* > adj_list;

		EdgeWeightedGraph(const string file_name) {
			ifstream in(file_name.c_str());

			if (!in) {
				cout << "Open file " << file_name << " Failed\n";
			}
			in >> v;
			string line;
			while (in.good() ) {
				//istringstream iss(line);

				int f, t;
				double weight;
				in >> f >> t >> weight;

				DirectedEdge edge(f, t, weight);
				cout << edge << endl;
				add_edge(edge);
			}

		}
		void add_edge(const DirectedEdge e) {
			int f = e.from;
			int t = e.to;
			double w = e.weight;

			map<int, vector<DirectedEdge> *>::iterator itr = adj_list.find(f);
			if (itr != adj_list.end() ) {
				adj_list[f]->push_back(e);
			} else {
				vector<DirectedEdge> *vec = new vector<DirectedEdge>();
				vec->push_back(e);
				adj_list.insert(pair<int, vector<DirectedEdge>* > (f, vec));
			}
		}

		vector<DirectedEdge> *adj(int v)  {
			return adj_list[v];
		}
	};

}
#endif