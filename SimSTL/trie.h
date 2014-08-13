/********************************************************************************************/
/* The file implementing Trie according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#ifndef __TRIE_H
#define  __TRIE_H

#include <string>
#include <vector>
#include <queue>

using namespace std;

namespace Algorithm {
	
	template <class Value>
	class Trie {
	public:
		Trie();
		~Trie();
		void put(string &key, Value value);
		Value get(string & key);
		vector<string> keys_with_prefix(string key);

	private:
		struct Node {
			Node() {
				next = new Node*[R];
				for (int i = 0; i < R; i++) {
					next[i] = NULL;
				}
				is_value = false;
			}

			Value value;
			Node ** next;
			bool is_value;
		};

		Node *put(Node *&node, string& key, Value &value, int d);
		Node* get(Node *node, string &key, int d);
		void collect(Node *node, string& prefix, queue<string>& res); 

		static const int R;
		Node *root;
	};

	template <class Value>
	Trie<Value>::Trie() {
		root = NULL;
	}

	template <class Value>
	Trie<Value>::~Trie() {

	}

	template <class Value>
	const int Trie<Value>::R = 256;

	template <class Value>
	void Trie<Value>::put( std::string &key, Value value) {
		put(root, key, value, 0);
	}

	template <class Value>
	typename Trie<Value>::Node * Trie<Value>::put(typename Trie<Value>::Node *&node, string &key, Value &value, int d) {
		if (node == NULL ) {
			node = new typename Trie<Value>::Node();
		}
		if (d == key.length()) {
			node->value = value;
			node->is_value = true;
			return node;
		}

		char c = key.at(d);
		node->next[c] = put(node->next[c], key, value, d + 1);
		return node;
	}


	template <class Value>
	Value Trie<Value>::get(string &key) {
		Node * val = get(root, key, 0);
		if (val == NULL) {
			return NULL;
		} 
		return val->value;
	}

	template <class Value>
	typename Trie<Value>::Node* Trie<Value>::get(typename Trie<Value>::Node *node, string &key, int d) {
		if (node == NULL) {
			return NULL;
		}

		if (d == key.length()) {
			return node;
		}
		char ch = key.at(d);
		return get(node->next[ch], key, d + 1);
	}

	template <class Value>
	vector<string> Trie<Value>::keys_with_prefix(string key) {
		queue<string> res;
		Node *node = get(root, key, 0);
		collect(node, key, res);
		vector<string> prefixs;
		
		while(!res.empty()) {
			prefixs.push_back(res.front());
			res.pop();
		}
		return prefixs;
	}
	
	template <class Value>
	void Trie<Value>::collect(typename Trie<Value>::Node *node, string &prefix, queue<string> &res) {
		if (node == NULL) return;

		if (node->is_value == true) {
			string prefs = prefix;
			res.push(prefs);
		}

		for (char c = 0; c < R; c++) {
			prefix.push_back(c);
			collect(node->next[c], prefix, res);
			prefix.erase(prefix.length() - 1);
		}
	}


}
#endif