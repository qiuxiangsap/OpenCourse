/********************************************************************************************/
/* The file implementing the binary search tree according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 5th, 2014 
/*********************************************************************************************/
#ifndef __BST_H
#define __BST_H

#include <queue>

namespace Algorithm {
		
	template <class Key, class Value>
	struct Node {
		Node(Key k, Value v, int n, Node *l, Node *r):key(k), value(v),size(n), left(l), right(r){ 
		}
		Key key;
		Value value;
		int size;
		Node *left, *right;

	};

	template <class Key, class Value>
	class BST {
	public:
		BST();
		~BST();

		void put(Key key, Value value);
		Value get(const Key &key);
		void delete_elem(const Key&key); 
		int size();
		bool is_empty();
		bool contains(const Key &key);
		std::queue<Key> level_order();

	private:
		Node<Key, Value>* put(Node<Key, Value> *node, Key key, Value value);
		Value get(Node<Key, Value> * node, const Key &key);
		void delete_tree(Node<Key, Value> *node);
		int size(const Node<Key, Value> *node);
		Node<Key, Value> delete_elem(Node<Key, Value> *node, const Key &key);
		Node<Key, Value> * min(Node<Key, Value> *node); // get the min of current tree rooted at node
		Node<Key, Value> * deleteMin(Node<Key, Value> *node); // delete the node with minimum element in current tree rooted at node

		Node<Key, Value> *root;
	};

	template<class Key, class Value>
	BST<Key, Value>::BST() {
		root = NULL;
	}

	template <class Key, class Value>
	BST<Key, Value>::~BST() {
		delete root;
		root = NULL;
	}

	template <class Key, class Value>
	void BST<Key, Value>::put(Key key, Value value) {
		root = put(root, key, value);
	}

	template <class Key, class Value>
	Node<Key, Value>* BST<Key, Value>::put(Node<Key, Value> *node, Key key, Value value) {
		if (node == NULL) {
			node = new Node<Key, Value>(key, value, 1, NULL, NULL);
			return node;
		}

		if (node->key > key ) {
			node->left = put(node->left, key, value);
		} else if (node->key == key) {
			node->value = value;
		} else {
			node->right = put(node->right, key, value);
		}
		node->size = 1 + size(node->left) + size(node->right);

		return node;
	}

	template <class Key, class Value>
	Value BST<Key, Value>::get(const Key &key) {
		return get(root, key);
	}
	template <class Key, class Value>
	Value BST<Key, Value>::get(Node<Key, Value> * node, const Key &key) {
		if (node == NULL) {
			return NULL;
		}
		if (node->key > key) {
			return get(node->left, key);
		} else if (node->key == key) {
			return node->value;
		} else {
			return get(node->right, key);
		}
	}

	template <class Key, class Value>
	void BST<Key, Value>::delete_tree(Node<Key, Value> *node) {
		if (node == null) {
			return;
		}
		if (node->left != null) {
			delete(node->left);
		}
		if (node->right != null) {
			delete node->right;
		}
		delete node;
	}

	template <class Key, class Value>
	int BST<Key, Value>::size() {
		return size(root);
	}

	template <class Key, class Value>
	int BST<Key, Value>::size(const Node<Key, Value> *node) {
		if (node == NULL) {
			return 0;
		} else {
			return node->size;
		}
	}

	template <class Key, class Value>
	void BST<Key, Value>::delete_elem(const Key&key) {
		delete(root, key);
	}

	template <class Key, class Value>
	Node<Key, Value> BST<Key, Value>::delete_elem(Node<Key, Value> *node, const Key &key) {
		if (is_empty()) {
			throw std::runtime_error("Empty Tree");
		}
		if (node == NULL) {
			return;
		}

		if (node->key > key) {
			node->left = delete(node->left, key);
		} else if (node->key < key) {
			node->right = delete(node->right, key);
		} else {
			if (node->left == NULL) return node->right;
			if (node->right == NULL) return node->left;
			Node *t = node;
			node = min(node->right);
			node->right = deleteMin(t->right);
			node->left = t->left;
		}
		node->size = size(node->left) + size(node->right) + 1;
	}

	template <class Key, class Value>
	Node<Key, Value> *BST<Key, Value>::min(Node<Key,Value> *node) {
		if (node == NULL) return;
		if (node->left == NULL) {
			return node;
		} else {
			return min(node->left);
		}
	}

	template <class Key, class Value>
	Node <Key, Value> *deleteMin(Node<Key, Value> *node ) {
		if (node == NULL) return;
		if (node->left == null) {
			return node->right;
		} else {
			node->left = deleteMin(node->left);
			
		}
		node->size = size(node->left) + size(node->right) + 1;
		return node;
	}

	template <class Key, class Value>
	bool BST<Key, Value>::is_empty() {
		return size() == 0;
	}

	template <class Key, class Value>
	bool BST<Key, Value>::contains(const Key &key) {
		return get(key) == NULL;
	}
	template <class Key, class Value>
	std::queue<Key> BST<Key, Value>::level_order() {
		//if (is_empty()) { return NULL;} 
		std::queue<Key> res;
		std::queue<Node<Key,Value> *> queue;
		queue.push(root);
		res.push(root->key);

		while (!queue.empty()) {
			Node<Key, Value> *node = queue.front();
			queue.pop();
			if (node->left != NULL) {
				queue.push(node->left);
				res.push(node->left->key);
			} 
			if (node->right != NULL) {
				queue.push(node->right);
				res.push(node->right->key);
			}
		}
		return res;
	}
}

#endif;