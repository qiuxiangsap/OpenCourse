/********************************************************************************************/
/* The file implementing KMP algorithm according to the recipe in Algorithms course 
/* in Princeton
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/

#ifndef __KMP_H
#define __KMP_H

#include <string>
using namespace std;

namespace Algorithm {

class StringMatch {
public:
	StringMatch(char *pattern, int len);
	int kmp(const string &origin);

private:
	int **dfa;
	char *pattern;
	int M;
	void kmp();
	const static int R;

};

const int StringMatch::R = 256;

int StringMatch::kmp(const string &origin) {
	int j = 0;
	int i = 0;
	for (i = 0, j = 0; i < origin.length() & j < M; i++) {
		j = dfa[origin.at(i)][j];
	}

	if (j == M ) {
		return (i - j);
	}
	return -1;
}
StringMatch::StringMatch(char *pat, int len) {
	pattern = new char[len];
	for (int i = 0; i < len; i++) {
		pattern[i] = pat[i];
	}

	dfa = new int*[R];
	for (int i = 0; i < R; i++) {
		dfa[i] = new int[len];
	}
	for (int i = 0 ; i < R; i++) {
		for (int j = 0; j < len; j++) {
			dfa[i][j] = 0;
		}
	}
	M = len;
	kmp();
}

void StringMatch::kmp() {
	dfa[pattern[0]][0] = 1;
	for (int X = 0, j = 1; j < M; j++) {
		for (int c = 0; c < R; c++) {
			dfa[c][j] = dfa[c][X];
		}
		dfa[pattern[j]][j] = j + 1;
		X = dfa[pattern[j]][X];
	}
}
}
#endif