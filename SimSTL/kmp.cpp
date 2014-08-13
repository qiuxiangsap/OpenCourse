#include "kmp.h"
#include <iostream>

using namespace std;

int main(int argc, char *argv[]) {
	Algorithm::StringMatch *s_match = new Algorithm::StringMatch("abc", 3);
	cout << s_match->kmp(string("eeabceee")) << endl;

	return 0;
}