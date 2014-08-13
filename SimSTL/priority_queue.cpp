#include "priority_queue.h"
#include <iostream>

using namespace std;
using namespace Algorithm;

int main(int argc, char ** argv) {
	Priority_Queue<int> pq(4);
	pq.insert(100);
	pq.insert(200);
	pq.insert(20);
	pq.insert(300);
	pq.insert(400);
	pq.insert(4);

	cout << "Minimum Element of the queue: " << pq.del_min() << endl;
	cout << "Current Size of the Priority Queue: " << pq.size() << endl;
	return 0;

}