#include "trie.h"
#include <iostream>
#include <string>
#include <algorithm>
#include <iterator>


using namespace std;

int main(int argc, char * argv[]) {
	Algorithm::Trie<int> *trie = new Algorithm::Trie<int>();
	string key = string("abc");
	string key2 = string("abcdef");
	trie->put(key, 10);
	trie->put(key2, 20);
	vector<string> prefixs = trie->keys_with_prefix(string("abc"));

	copy(prefixs.begin(), prefixs.end(), ostream_iterator<string>(cout, "\n"));
	//cout << "Get Value: " << trie->get(key) << endl;

	return 0;
}