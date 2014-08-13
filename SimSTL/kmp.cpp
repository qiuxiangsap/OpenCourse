/********************************************************************************************/
/* The file implementing the KMP test client code
/* 
/* Author: Zhongcun Wang
/* Date  : Augugst 12th, 2014 
/*********************************************************************************************/
#include "kmp.h"
#include <iostream>

using namespace std;

int main(int argc, char *argv[]) {
	Algorithm::StringMatch *s_match = new Algorithm::StringMatch("abc", 3);
	cout << s_match->kmp(string("eeabceee")) << endl;

	return 0;
}