/********************************************************************************************/
/* The file implementing Graph according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#ifndef __GRAPH_H
#define __GRAPH_H

#include <vector>
#include <map>
#include <fstream>
#include <iostream>
#include <stdexcept>
#include <string>

using namespace std;

namespace Algorithm{

	struct UDGraph {
	private:
		int n;
		map<int, vector<int> * > adj_list;
	public:
		UDGraph(int n):n(n) {

		}

		UDGraph(const string &file_name) {
			ifstream in(file_name.c_str());

			if (!in) {
				cerr << file_name.c_str() << " Doesn't Exist!" << endl;
				exit(1);
			}

			in >> n;
			while( in.good() ) {
				int f, t;
				in >> f >> t;
				add_edge(f, t);
				add_edge(t, f);
			}
		}

		void add_edge(int v, int w) {
			map<int, vector<int> * >::iterator itr = adj_list.find(v);

			if (itr != adj_list.end()) {
				itr->second->push_back(w);
			} else {
				vector<int> *vec = new vector<int>();
				vec->push_back(w);
				adj_list.insert(pair<int, vector<int> *>(v, vec));
			}
		}

		vector<int> *adj(int v) {
			return adj_list[v];
		}

		int v() {
			return n;
		}
	};



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
				cout << "Open file " << file_name.c_str() << " Failed\n";
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